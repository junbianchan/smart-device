package co.darma.smartmattress.analysis.service.impl;

import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.analysis.dao.BodyMotionDao;
import co.darma.smartmattress.analysis.dao.OpticalDataDao;
import co.darma.smartmattress.analysis.entity.*;
import co.darma.smartmattress.analysis.handle.HistoryPacketHandleService;
import co.darma.smartmattress.analysis.healthAlgorithms.basic.DMAlgorithmMattress;
import co.darma.smartmattress.analysis.service.PacketAnalysisService;
import co.darma.smartmattress.analysis.util.ArrayHandle;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.entity.DeviceCookie;
import co.darma.smartmattress.util.LogUtil;
import org.apache.log4j.Logger;

import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 15/10/12.
 */
public class DefaultHealthPacketAnalysisService implements PacketAnalysisService {

    public static final String HEALTH_DATA = "healthData";

    public static final String STATUS_DATA = "statusData";

    public static final String PACKET_LIST = "packetList";

    public static final String DATA_BEGIN_INDEX = "dataBeginIndex";

    public static final int MOTION = 3;

    public static final int MOTION_SKIP_SCOPE = 8;

    public static final String USER_ID = "userId";

    public static final String OPTICAL_DATA = "opticalData";

    private BodyMotionDao bodyMotionDao;

    private OpticalDataDao opticalDataDao;

    private static Logger logger = Logger.getLogger(DefaultHealthPacketAnalysisService.class);

    private DMAlgorithmMattress mattress = new DMAlgorithmMattress();

    private int getTotalLength(List<ServicePacket> packetList) {
        int total = 0;
        for (ServicePacket p : packetList) {
            total += p.getIntData().length;
        }
        return total;
    }

    @Override
    public void analysis(ServiceContext input, ServiceContext output) {

        List<ServicePacket> packetList = (List<ServicePacket>) input.getArgByName(PACKET_LIST);

        String deviceNo = packetList.get(0).getDeviceNo();

        Device device = (Device) input.getArgByName(HistoryPacketHandleService.DEVICE);
        if (device == null) {
            logger.error("Device Id is null ,the packet is invalid");
            return;
        }

        try {
            filterExceptionPacket(packetList, deviceNo);

            //不满足11秒数据，如果计算的话，会导致程序崩溃。
            //2016-01-27 崩溃问题已经解决，但是如果数据过少的话，没有计算的必要，结果也计算不出来
            if (packetList.size() < QueueUtil.HISTORY_PACKET_AT_LEAST_NUMBER) {
                logger.info("Two few packet drop them" + packetList);
                return;
            }

            long startTime1 = System.currentTimeMillis();

            int[] opticData = combinate2ByteArray(packetList);

            // calculate to the hr data;
            int[] heartRateDatas = calHeartRate(opticData);

            long startTimehr = System.currentTimeMillis();
            logger.info("device :" + deviceNo + " heart rate cost :" + (startTimehr - startTime1));

            // calculate the breath value
            int[] breathRateDatas = mattress.offlineBRCalculation(opticData);

            long startTimebr = System.currentTimeMillis();
            logger.info("device :" + deviceNo + " breath cost :" + (startTimebr - startTimehr));

            // calculate move status
            int[] statusDatas = mattress.offlineStatusCalForMeddo(opticData);

            long endTime = System.currentTimeMillis();
            logger.info("device :" + deviceNo + " statusDatas cost :" + (endTime - startTimebr));

            OpticalData opticalData = new OpticalData();
            opticalData.setStartTime(packetList.get(0).getTimestamp());
            opticalData.setEndTime(packetList.get(packetList.size() - 1).getTimestamp());
            opticalData.setUpdateTime(System.currentTimeMillis());
            opticalData.setDevice(device);
            opticalData.setOpticalData(ArrayHandle.intToString(0, opticData));
            opticalDataDao.saveOpticalData(opticalData);
            logger.info("Device " + deviceNo + " save optical Data cost :" + (System.currentTimeMillis() - endTime));

            if (logger.isDebugEnabled()) {
                logger.debug("Device " + deviceNo + " Optic data:" + ArrayHandle.intToString(0, opticData));
                logger.debug("Device " + deviceNo + " Heart rate is :" + ArrayHandle.intToString(0, heartRateDatas));
                logger.debug("Device " + deviceNo + " BreathData :" + ArrayHandle.intToString(0, breathRateDatas));
                logger.debug("Device " + deviceNo + " Status  :" + ArrayHandle.intToString(0, statusDatas));
            }

            List<HealthData> healthList = new LinkedList<HealthData>();
            List<BodyMotion> bodyMotionList = new LinkedList<BodyMotion>();

            for (int step = 0; // skip the cache
                 step < packetList.size(); ++step) {
                ServicePacket packet = packetList.get(step);

                HealthData healthData = new HealthData();
                healthData.setDevice(device);
                healthData.setMarkTime(packet.getTimestamp());
                healthData.setHeartRate(heartRateDatas[step % heartRateDatas.length]);
                healthData.setBreathValue(breathRateDatas[step % breathRateDatas.length]);
                healthData.setAlgorithmVersion(mattress.getVersion());
                healthData.setUserId((Integer) output.getArgByName("userId"));
                healthList.add(healthData);
            }

            for (int step = 0; step < packetList.size(); ) {

                if ((statusDatas[step % statusDatas.length] != 2)
                        && (statusDatas[step % statusDatas.length] >= 0)) {// 躺着在床上不保存

                    ServicePacket packet = packetList.get(step);

                    //为了应对，在上个六十秒计算中最后的7秒数据中，有一个体动，那么在本次的前7秒数据中，
                    //,可能会有数据是在可忽略的间隔内。（每次出现体动之后，改体动8秒钟以内的体动不算一次体动。）
                    if (step < (MOTION_SKIP_SCOPE - 1)) {
                        //加这个的意义在于，在上一分钟，可能存在有数据是连续抖动的，然后它的下半部分和这个数据的上半部分是相邻的
                        BodyMotion motion = checkMotionRecordYet(device.getId(), packet.getTimestamp());
                        if (motion != null) {
                            logger.info("last body motion is :" + motion + ",current is :" + packet.getTimestamp() + " ,step is :" + step);
                            int distance = (int) (packet.getTimestamp() - motion.getMarkTime());
                            step = step + MOTION_SKIP_SCOPE - distance;
                            //这个是个小优化，如果判断过了，那么下次不用再判断
                            //如果已经进入过这个判断一次了，下次就不用再进入了
                            continue;
                        }
                    }
                    BodyMotion bm = new BodyMotion();
                    bm.setDevice(device);
                    bm.setMarkTime(packet.getTimestamp());
                    bm.setAlgorithmVersion(mattress.getVersion());
                    bm.setLevel(BodyMotionType.getBodyMotionTypeByValue(MOTION));
                    bm.setUserId((Integer) output.getArgByName(USER_ID));
                    bodyMotionList.add(bm);
                    step += MOTION_SKIP_SCOPE;
                } else {
                    ++step;
                }
            }

            //TODO
            long starTime3 = System.currentTimeMillis();

            bodyMotionDao.saveOrUpdateBodyMotionList(bodyMotionList);

            logger.info("save cost :" + (System.currentTimeMillis() - starTime3));

            output.putArgument(HEALTH_DATA, healthList);
            output.putArgument(STATUS_DATA, bodyMotionList);
            output.putArgument(OPTICAL_DATA, opticData);

        } catch (Exception e) {
            logger.error(LogUtil.traceException(e));
        }

        logger.info("End to analysis packet");
    }

    /**
     * 过滤掉登录前的异常包
     *
     * @param packetList
     */
    private void filterExceptionPacket(List<ServicePacket> packetList, String deviceNo) {
        DeviceCookie cookie = QueueUtil.getDeviceCookie(deviceNo);
        //cookie 在，并且在床，并且时间比在上床之后的数据才可以。
        if (cookie != null) {
            if (cookie.getOnBed()) {
                Long upBedTime = cookie.getLastUpBedTime();
                Iterator<ServicePacket> packetIterator = packetList.iterator();
                while (packetIterator.hasNext()) {
                    ServicePacket packet = packetIterator.next();
                    if (packet.getTimestamp() < upBedTime) {
                        packetIterator.remove();
                        logger.info("remove packet :" + packet.getTimestamp());
                    } else {
                        return;
                    }
                }
            } else {
                //离开床的包丢了，这个无所谓，不一个过滤任何包
            }
        } else {
            //上床的包丢失了,或者已经离开床了
            cookie = new DeviceCookie(deviceNo);
            cookie.setLastLoginTime(System.currentTimeMillis());
            cookie.setOnBed(true);
            cookie.setLastUpBedTime(packetList.get(0).getTimestamp());
            QueueUtil.updateDeviceCookie(cookie);
        }
    }

    /**
     * 通过设备ID，还有当前的体动时间，获取
     *
     * @param deviceId
     * @param timestamp
     * @return
     */
    private BodyMotion checkMotionRecordYet(Integer deviceId, long timestamp) {
        return bodyMotionDao.queryBodyMotionWithDeviceIdAndTime(deviceId, timestamp - (MOTION_SKIP_SCOPE - 1), timestamp);
    }

    private int[] calHeartRate(int[] opticData) {

        int[] result = mattress.offlineHRCalculation(opticData);

        if (result != null && result[0] != -1 && result[result.length - 1] != -1) {
            return result;
        }
        if (opticData.length >= QueueUtil.HISTORY_PACKET_AT_LEAST_NUMBER * 2) {

            int firstHalfLength = (opticData.length) / 2;
            int[] firstHalfArray = new int[firstHalfLength];
            int[] secondHalfArray = new int[opticData.length - firstHalfLength];
            System.arraycopy(opticData, 0, firstHalfArray, 0, firstHalfLength);
            System.arraycopy(opticData, firstHalfLength, secondHalfArray, 0, secondHalfArray.length);
            int[] firsthHalfResult = mattress.offlineHRCalculation(firstHalfArray);
            int[] secondHalfResult = mattress.offlineHRCalculation(secondHalfArray);

            logger.info("firsthHalfResult " + firsthHalfResult[0] + ",secondHalfResult" + secondHalfResult[0]);
            if (firsthHalfResult[0] > 0 || secondHalfResult[0] > 0) {
                //至少有一段数据是好的才要
                int[] finalResutl = new int[firsthHalfResult.length + secondHalfResult.length];

                System.arraycopy(firsthHalfResult, 0, finalResutl, 0, firsthHalfResult.length);
                System.arraycopy(secondHalfResult, 0, finalResutl, firsthHalfResult.length, secondHalfResult.length);

                return finalResutl;
            }
        }
        return result;

        // 不满足要求的情况用整体的计算，不分段计算
    }

    private int[] combinate2ByteArray(List<ServicePacket> packetList) {
        int allData[] = new int[getTotalLength(packetList)];
        int start = 0;
        for (ServicePacket p : packetList) {
            System.arraycopy(p.getIntData(), 0, allData, start, p.getIntData().length);
            start += p.getIntData().length;
        }
        return allData;
    }


    public void setBodyMotionDao(BodyMotionDao bodyMotionDao) {
        this.bodyMotionDao = bodyMotionDao;
    }

    public void setOpticalDataDao(OpticalDataDao opticalDataDao) {
        this.opticalDataDao = opticalDataDao;
    }
}
