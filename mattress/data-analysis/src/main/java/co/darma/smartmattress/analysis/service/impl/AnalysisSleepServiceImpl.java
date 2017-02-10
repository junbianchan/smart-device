package co.darma.smartmattress.analysis.service.impl;

import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.analysis.dao.MeddoHealthDataDao;
import co.darma.smartmattress.analysis.dao.MotionForSleepDao;
import co.darma.smartmattress.analysis.entity.BodyMotionForSleep;
import co.darma.smartmattress.analysis.entity.MeddoHealthData;
import co.darma.smartmattress.analysis.entity.SleepState;
import co.darma.smartmattress.analysis.healthAlgorithms.basic.DMAlgorithmMattress;
import co.darma.smartmattress.analysis.service.AnalysisSleepService;
import co.darma.smartmattress.analysis.util.ArrayHandle;
import co.darma.smartmattress.dao.DeviceDao;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.entity.DeviceCookie;
import co.darma.smartmattress.util.ByteUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 16/1/4.
 */
public class AnalysisSleepServiceImpl implements AnalysisSleepService {

    public static final int FIVE_MINUTES = 5 * 60;

    private MotionForSleepDao motionForSleepDao;

    private DMAlgorithmMattress mattress = new DMAlgorithmMattress();

    private MeddoHealthDataDao meddoHealthDataDao;

    private static final Logger logger = Logger.getLogger(AnalysisSleepServiceImpl.class);

    @Override
    public List<SleepState> analysisSleepData(Device device, Long endTime) {

        logger.info("device is :" + device + ",endTime:" + endTime);

        DeviceCookie cookie = QueueUtil.getDeviceCookie(device.getDeviceNo());
        //在床上
        if (cookie != null && cookie.getOnBed()) {
            Long startTime = cookie.getLastUpBedTime();
            //上床到现在这段时间的睡眠数据
            return analysisSleepStates(device, startTime, endTime);
        }
        return null;
    }

    /**
     * 计算开始时间到结束时间的，连续一段时间的数据的睡眠状况
     *
     * @param device
     * @param endTime
     * @param startTime
     * @return
     */
    public List<SleepState> analysisSleepStates(Device device, Long startTime, Long endTime) {

        //TODO 这里其实是有问题的，有可能当时的一部分体动数据还没有保存起来
        List<BodyMotionForSleep> bodyMotionForSleepList = motionForSleepDao.queryBodyMotionForSleep(device.getId(), startTime, endTime);

        Long startTimeForHealth = (startTime / 60) * 60;

        List<MeddoHealthData> healthData = meddoHealthDataDao.queryHealthDataByDevice(device.getId(), startTimeForHealth, endTime);

        int[] motionRecord = getBodyMotionReord(bodyMotionForSleepList);
        int[] heartRates = getHeartRate(healthData);
        int[] breath = getBreath(healthData);

        if (motionRecord == null || heartRates == null || breath == null) {
            logger.error("motionRecord , heartRates or breath is null");
            return null;
        }

        //TODO 需要删除
        //logger.info("device : " + device.getDeviceNo() + ", motion:" + ArrayHandle.intToString(0, motionRecord));
        //logger.info("device : " + device.getDeviceNo() + ", heartRates:" + ArrayHandle.intToString(0, heartRates));
        //logger.info("device : " + device.getDeviceNo() + ", breath:" + ArrayHandle.intToString(0, breath));

        int[] sleepState = mattress.sleepStageAnalysis(motionRecord, heartRates, breath);
        //TODO
        logger.info("sleepState is :" + ArrayHandle.intToString(0, sleepState));

        List<SleepState> sleepStateList = new LinkedList<SleepState>();

        int lastState = -1;

        long currentTime = System.currentTimeMillis();
        Long currentStateTime = bodyMotionForSleepList.get(0).getStartTime();

        for (int i = 0; i < sleepState.length - 1; ++i) {
            if (sleepState[i] != lastState) {
                SleepState state = new SleepState();
                state.setDevice(device);
                state.setStartTime(currentStateTime);
                state.setEndTime(currentStateTime + FIVE_MINUTES - 1);
                state.setSleepState(sleepState[i]);
                state.setUpdateTime(currentTime);
                sleepStateList.add(state);
                lastState = sleepState[i];
                currentStateTime += FIVE_MINUTES;
            } else {
                //状态没有变化
                SleepState state = sleepStateList.get(sleepStateList.size() - 1);
                state.setEndTime(currentStateTime + FIVE_MINUTES - 1);
                currentStateTime += FIVE_MINUTES;
            }
        }

        // 还剩下一个
        if (sleepState[sleepState.length - 1] != lastState) {
            SleepState state = new SleepState();
            state.setDevice(device);

            if (currentStateTime <=
                    bodyMotionForSleepList.get(bodyMotionForSleepList.size() - 1).getEndTime()) {
                state.setStartTime(currentStateTime);
                state.setEndTime(bodyMotionForSleepList.get(bodyMotionForSleepList.size() - 1).getEndTime());
            } else {
                state.setStartTime(currentStateTime);
                state.setEndTime(currentStateTime + FIVE_MINUTES - 1);
            }
            state.setSleepState(sleepState[sleepState.length - 1]);
            state.setUpdateTime(currentTime);
            sleepStateList.add(state);
        } else {
            SleepState state = sleepStateList.get(sleepStateList.size() - 1);
            if (state.getEndTime() < bodyMotionForSleepList.get(bodyMotionForSleepList.size() - 1).getEndTime()) {
                state.setEndTime(bodyMotionForSleepList.get(bodyMotionForSleepList.size() - 1).getEndTime());
            }
        }
        return sleepStateList;
    }

    private int[] getBreath(List<MeddoHealthData> healthData) {

        if (CollectionUtils.isNotEmpty(healthData)) {

            int[] breath = new int[healthData.size()];

            int i = 0;
            for (MeddoHealthData data : healthData) {
                breath[i++] = data.getBreathValue();
            }
            return breath;
        }

        return null;
    }

    private int[] getHeartRate(List<MeddoHealthData> healthData) {

        if (CollectionUtils.isNotEmpty(healthData)) {

            int[] heartRate = new int[healthData.size()];

            int i = 0;
            for (MeddoHealthData data : healthData) {
                heartRate[i++] = data.getHeartRate();
            }
            return heartRate;
        }

        return null;
    }

    private int[] getBodyMotionReord(List<BodyMotionForSleep> bodyMotionForSleepList) {

        if (CollectionUtils.isNotEmpty(bodyMotionForSleepList)) {
            int totalSize = getSizeFromList(bodyMotionForSleepList);
            int[] allData = new int[totalSize];
            int step = 0;
            for (BodyMotionForSleep motion : bodyMotionForSleepList) {
                String[] records = motion.getMetaData().split(",");
                int[] intRecord = strToInt(records);
                System.arraycopy(intRecord, 0, allData, step, intRecord.length);
                step += intRecord.length;
            }

            return allData;
        }
        return null;
    }

    private int getSizeFromList(List<BodyMotionForSleep> bodyMotionForSleepList) {
        int totalSize = 0;

        for (BodyMotionForSleep motion : bodyMotionForSleepList) {
            totalSize += motion.getDataNumber();
        }

        return totalSize;
    }

    private int[] strToInt(String[] records) {
        int[] result = new int[records.length];

        int i = 0;
        for (String s : records) {
            if (s.length() == 1) {
                result[i++] = (s.charAt(0) - '0');
            }
        }
        return result;
    }

    public void setMotionForSleepDao(MotionForSleepDao motionForSleepDao) {
        this.motionForSleepDao = motionForSleepDao;
    }

    public void setMeddoHealthDataDao(MeddoHealthDataDao meddoHealthDataDao) {
        this.meddoHealthDataDao = meddoHealthDataDao;
    }
}

//
//    public static void main(String[] args) {
//        List<BodyMotionForSleep> bodyMotionForSleepList = new LinkedList<BodyMotionForSleep>();
//
//
//        BodyMotionForSleep s1 = new BodyMotionForSleep();
//        s1.setDataNumber(5);
//        s1.setMetaData("1,2,3,4,5");
//
//        bodyMotionForSleepList.add(s1);
//
//
//        BodyMotionForSleep s2 = new BodyMotionForSleep();
//
//        s2.setDataNumber(6);
//        s2.setMetaData("6,7,8,9,9,0");
//
//        bodyMotionForSleepList.add(s2);
//
//
//        AnalysisSleepServiceImpl s = new AnalysisSleepServiceImpl();
//        int[] result = s.getBodyMotionReord(bodyMotionForSleepList);
//
//        for (int r : result) {
//            System.out.print(Integer.valueOf(r) + ",");
//        }
//
//
//    }