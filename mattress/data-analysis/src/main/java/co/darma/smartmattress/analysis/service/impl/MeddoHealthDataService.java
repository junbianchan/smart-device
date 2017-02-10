package co.darma.smartmattress.analysis.service.impl;

import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.analysis.dao.MeddoHealthDataDao;
import co.darma.smartmattress.analysis.entity.HealthData;
import co.darma.smartmattress.analysis.entity.MeddoHealthData;
import co.darma.smartmattress.analysis.entity.ServiceContext;
import co.darma.smartmattress.analysis.handle.HistoryPacketHandleService;
import co.darma.smartmattress.analysis.service.PacketAnalysisService;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.entity.DeviceCookie;
import co.darma.smartmattress.util.LogUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 15/11/25.
 */
public class MeddoHealthDataService implements PacketAnalysisService {

    private static final Double DEFFAULT_HEART_BREATH_REATE = 4.0;

    private MeddoHealthDataDao meddoHealthDataDao;

    private static final Logger logger = Logger.getLogger(MeddoHealthDataService.class);

    @Override
    public void analysis(ServiceContext input, ServiceContext output) {

        List<HealthData> healthDataList = (List<HealthData>)
                output.getArgByName(DefaultHealthPacketAnalysisService.HEALTH_DATA);

        Device device = (Device) input.getArgByName(HistoryPacketHandleService.DEVICE);

        if (device == null) {
            logger.error("Device Id is null ,the packet is invalid");
            return;
        }

        String deviceNo = device.getDeviceNo();

        if (CollectionUtils.isNotEmpty(healthDataList)) {
            List<MeddoHealthData> meddoHealthDataList = parserToMinitueAverage(healthDataList, device);

            if (CollectionUtils.isEmpty(meddoHealthDataList)) {
                logger.info("meddoHealthDataList is empty.");
                return;
            }
            //把上半段和上次的半段拿出来，作为全新的一分钟
            if (meddoHealthDataList.size() > 1) {
                //跨越了两分钟
                MeddoHealthData first = meddoHealthDataList.get(0);
                MeddoHealthData second = meddoHealthDataList.get(1);
                calAvrageDataWithLastMinutue(deviceNo, first);
                //缓存这一分钟的临时数据，用于下次计算
                QueueUtil.putHalfPacketToCache(deviceNo, second);
                meddoHealthDataList.remove(1);
            } else {
                MeddoHealthData healthData = meddoHealthDataList.get(0);
                calAvrageDataWithLastMinutue(deviceNo, healthData);
                QueueUtil.putHalfPacketToCache(deviceNo, healthData);
            }
            //完整的一分钟
            MeddoHealthData healthData = meddoHealthDataList.get(0);
            refreshHeartBreathRate(healthData, deviceNo);
            updateIfDataNotRight(device.getDeviceNo(), healthData);

            //缓存本次的数据
            cacheHealthData(deviceNo, healthData);

            logger.info("health data calculate final Result is :" + healthData);

            meddoHealthDataDao.saveMeddoHealthData(healthData);
        } else {
            logger.error("healthDataList is null");
        }

        DeviceCookie deviceCookie = QueueUtil.getDeviceCookie(deviceNo);
        //断网，或者用户离床
        if (deviceCookie == null
                || (deviceCookie != null && (!deviceCookie.getOnBed()))) {
            refreshCacheToDb(deviceNo);
            logger.info("Device is offline or people isn't on bed." + deviceCookie);
        }

    }

    private void cacheHealthData(String deviceNo, MeddoHealthData healthData) {
        //计算后的数据可靠

        if (healthData.getHeartRate() > 0) {
            logger.info("Prepare to cache packet :" + healthData);

            List<MeddoHealthData> pastDatas = (List<MeddoHealthData>) QueueUtil.getLastPacketBy(deviceNo);

            if (pastDatas == null) {
                pastDatas = new LinkedList<MeddoHealthData>();
            }
            if (pastDatas.size() >= 3) {
                pastDatas.remove(pastDatas.size() - 1);
            }
            pastDatas.add(0, healthData);
            //缓存最新的数据
            QueueUtil.cachePacket(deviceNo, pastDatas);
        }
    }

    /**
     * 将上一分钟缓存数据存储起来
     *
     * @param deviceNo
     */
    private void refreshCacheToDb(String deviceNo) {
        MeddoHealthData lastMinutePacket = (MeddoHealthData) QueueUtil.popHalfPacket(deviceNo);

        updateIfDataNotRight(deviceNo, lastMinutePacket);

        if (lastMinutePacket != null) {
            meddoHealthDataDao.saveMeddoHealthData(lastMinutePacket);
        }

    }

    /**
     * 会对心率进行估算，如果发现过大，那么就会修正；
     * 呼吸也是如此。
     *
     * @param deviceNo
     * @param healthData
     */
    private void updateIfDataNotRight(String deviceNo, MeddoHealthData healthData) {
        if (healthData != null) {

            //呼吸数据不可靠
            if (healthData.getBreathValue() < 0) {
                calAvgBreathData(healthData, deviceNo);
            }

            //心率数据不可靠
            if (healthData.getHeartRate() < 0 || healthData.getHeartRate() > 100) {
                calAvgHeartData(healthData, deviceNo);
            }

        }
    }

    /**
     * 刷新 (心率/呼吸) 的值
     *
     * @param healthData
     */
    private void refreshHeartBreathRate(MeddoHealthData healthData, String deviceNo) {
        try {
            DecimalFormat df = new DecimalFormat("#.00");
            if (healthData.getHeartRate() > 0 && healthData.getBreathValue() > 0) {
                Double rate = Double.valueOf(df.format(healthData.getHeartRate() / healthData.getBreathValue()));
                if (rate <= 6 && rate >= 3) {
                    Double lastRate = (Double) QueueUtil.getHeartBreathRate(deviceNo);
                    if (lastRate == null) {
                        lastRate = DEFFAULT_HEART_BREATH_REATE;
                    }
                    double currentRate = Double.valueOf(df.format(
                            (lastRate * 2 + rate) / (2 + 1)));
                    QueueUtil.updateHeartBreathRate(deviceNo, currentRate);
                }
            }
        } catch (Exception e) {
            logger.error("refreshHeartBreathRate error :" + LogUtil.logException(e));
        }

    }

    /**
     * 辅助analysis方法，用于将当前数据和上一份分钟剩下的数据进行平均
     *
     * @param deviceNo
     * @param healthData
     */
    private void calAvrageDataWithLastMinutue(String deviceNo, MeddoHealthData healthData) {

        //取出上次缓存的数据
        MeddoHealthData lastMinutePacket = (MeddoHealthData) QueueUtil.popHalfPacket(deviceNo);

        if (lastMinutePacket != null && lastMinutePacket.getMarkTime().equals(healthData.getMarkTime())) {

            int heartRate = healthData.getHeartRate() * healthData.getHeartRateWeight() +
                    lastMinutePacket.getHeartRate() * lastMinutePacket.getHeartRateWeight();

            int heartRateWeight = healthData.getHeartRateWeight() + lastMinutePacket.getHeartRateWeight();
            healthData.setHeartRateWeight(heartRateWeight);
            if (heartRateWeight == 0) {
                healthData.setHeartRate(-1);
            } else {
                healthData.setHeartRate(heartRate / heartRateWeight);
            }

            int breath = healthData.getBreathValue() * healthData.getBreathValueWeight()
                    + lastMinutePacket.getBreathValue() * lastMinutePacket.getBreathValueWeight();

            int breathWeight = healthData.getBreathValueWeight() + lastMinutePacket.getBreathValueWeight();
            healthData.setBreathValueWeight(breathWeight);

            if (breathWeight == 0) {
                healthData.setBreathValue(-1);
            } else {
                healthData.setBreathValue(breath / breathWeight);
            }
        }
    }

    /**
     * 将每一分的数据进行平均
     *
     * @param srcHealthData
     * @param device
     * @return
     */
    private List<MeddoHealthData> parserToMinitueAverage(List<HealthData> srcHealthData, co.darma.smartmattress.entity.Device device) {
        Long currentTime = System.currentTimeMillis();
        long lastMinute = -1;
        List<MeddoHealthData> meddoHealthDataList = new LinkedList<MeddoHealthData>();
        int heartCount = 0;
        int breathCount = 0;
        int totalHeartRate = -1;
        int totalBreathValue = -1;
        for (int i = 0;
             i < srcHealthData.size(); ) {
            HealthData manHealthData = srcHealthData.get(i);
            long time = manHealthData.getMarkTime();
            if (lastMinute == -1) {
                lastMinute = time / 60;
            }
            if ((time / 60) != lastMinute) {
                MeddoHealthData meddoHealthData = new MeddoHealthData(manHealthData, device);
                meddoHealthData.setMarkTime(lastMinute * 60);//记录上一个分钟数据
                if (heartCount > 0) {
                    //除数如果为0为导致报错，设置成1，不影响结果
                    meddoHealthData.setHeartRate(totalHeartRate / heartCount);
                } else {
                    meddoHealthData.setHeartRate(totalHeartRate);
                }

                meddoHealthData.setHeartRateWeight(heartCount);

                if (breathCount > 0) {
                    //呼吸没有这个问题
                    meddoHealthData.setBreathValue(totalBreathValue / breathCount);
                } else {
                    //呼吸没有这个问题
                    meddoHealthData.setBreathValue(totalBreathValue);
                }
                meddoHealthData.setBreathValueWeight(breathCount);
                meddoHealthData.setLastUpdateTime(currentTime);
                meddoHealthDataList.add(meddoHealthData);
                lastMinute = time / 60;
                totalHeartRate = -1;
                totalBreathValue = -1;
                heartCount = 0;
                breathCount = 0;
            } else {
                if (manHealthData.getHeartRate() > 0) {
                    if (totalHeartRate == -1) {
                        totalHeartRate = 0;
                    }
                    totalHeartRate += manHealthData.getHeartRate();
                    heartCount++;
                }

                if (manHealthData.getBreathValue() > 0) {
                    if (totalBreathValue == -1) {
                        totalBreathValue = 0;
                    }
                    totalBreathValue += manHealthData.getBreathValue();
                    breathCount++;
                }
                i++;
            }
        }
        //遗漏
        if (breathCount != 0) {
            MeddoHealthData meddoHealthData = new MeddoHealthData(srcHealthData.get(srcHealthData.size() - 1), device);
            meddoHealthData.setMarkTime(lastMinute * 60);
            if (heartCount > 0) {
                //除数如果为0为导致报错，设置成1，不影响结果
                meddoHealthData.setHeartRate(totalHeartRate / heartCount);
            } else {
                meddoHealthData.setHeartRate(totalHeartRate);
            }

            if (breathCount > 0) {
                meddoHealthData.setBreathValue(totalBreathValue / breathCount);
            } else {
                meddoHealthData.setBreathValue(totalBreathValue);
            }

            meddoHealthData.setHeartRateWeight(heartCount);
            meddoHealthData.setBreathValueWeight(breathCount);
            meddoHealthData.setLastUpdateTime(currentTime);
            meddoHealthDataList.add(meddoHealthData);
        }

        return meddoHealthDataList;
    }

    /**
     * 如果心率出现异常，那么需要将数据进行平均
     *
     * @param currentData
     * @param deviceNo
     * @return
     */
    private void calAvgHeartData(MeddoHealthData currentData, String deviceNo) {

        List<MeddoHealthData> pastDatas = (List<MeddoHealthData>) QueueUtil.getLastPacketBy(deviceNo);
        logger.info("pastData:" + pastDatas);
        if (CollectionUtils.isNotEmpty(pastDatas)) {
            MeddoHealthData lastMinuteData = pastDatas.get(0);
            if ((currentData.getMarkTime() / 60 - lastMinuteData.getMarkTime() / 60) < 3) {
                int heartRateTotal = 0;
                int heartRateCount = 0;
                for (MeddoHealthData data : pastDatas) {
                    heartRateTotal += (data.getHeartRate() * data.getHeartRateWeight());
                    heartRateCount += data.getHeartRateWeight();
                }
                heartRateTotal += (currentData.getHeartRate() * currentData.getHeartRateWeight());
                heartRateCount += currentData.getHeartRateWeight();

                if (heartRateCount > 0) {
                    currentData.setHeartRate(heartRateTotal / heartRateCount);
                    currentData.setReallyData(currentData.getReallyData() + 1);
                    if (currentData.getBreathValueWeight() > 0) {
                        currentData.setHeartRateWeight(currentData.getBreathValueWeight());
                    } else {
                        currentData.setHeartRateWeight(60);
                    }
                    logger.info("heart rate cal by average ...");
                    return;
                }
                //心率算不出来
            } else {
                //可能离床过，数据不整齐，不连续
                logger.error("Cal average heart data form history data,but the data is too old.");
                QueueUtil.clearCache(deviceNo);
                //心率算不出来
            }
        }
        //心率算不出来,呼吸是正常的,用呼吸计算心率
        //来到这里，证明没有计算出来，那么用另外一个方式来进行计算，心率=4.x * 呼吸
        if (currentData.getBreathValue() > 10) {
            Double heartBreathRate = (Double) QueueUtil.getHeartBreathRate(deviceNo);
            if (heartBreathRate == null) {
                heartBreathRate = DEFFAULT_HEART_BREATH_REATE;
            }
            Long heartRate = Math.round(currentData.getBreathValue() * heartBreathRate);
            currentData.setHeartRate(heartRate.intValue());
            currentData.setHeartRateWeight(currentData.getBreathValueWeight());
            currentData.setReallyData(currentData.getReallyData() + 1);
        }

    }

    /**
     * 计算呼吸数据
     *
     * @param healthData
     * @param deviceNo
     */
    private void calAvgBreathData(MeddoHealthData healthData, String deviceNo) {

        List<MeddoHealthData> pastDatas = (List<MeddoHealthData>) QueueUtil.getLastPacketBy(deviceNo);

        int breathValue = 0;
        if (CollectionUtils.isNotEmpty(pastDatas)) {
            MeddoHealthData lastCacheHealthData = pastDatas.get(0);
            if ((healthData.getMarkTime() / 60) - (lastCacheHealthData.getMarkTime() / 60) < 2) {

                int breathTotal = 0;
                int breathCount = 0;
                for (MeddoHealthData data : pastDatas) {
                    breathTotal += (data.getBreathValue() * data.getBreathValueWeight());
                    breathCount += data.getBreathValueWeight();
                }
                breathTotal += (healthData.getBreathValue() * healthData.getBreathValueWeight());
                breathCount += healthData.getBreathValueWeight();

                if (breathCount > 0) {
                    healthData.setBreathValue(breathTotal / breathCount);
                    healthData.setReallyData(healthData.getReallyData() + 3);

                    if (healthData.getHeartRateWeight() > 0) {
                        healthData.setBreathValueWeight(healthData.getHeartRateWeight());
                    } else {
                        healthData.setBreathValueWeight(60);
                    }
                    logger.info("breath rate cal by average ...");
                    return;
                }

            } else {
                //可能离床过，数据不整齐，不连续
                logger.error("Cal average breath data form history data,but the data is too old.");
                QueueUtil.clearCache(deviceNo);
                //心率算不出来
            }
        }
        //还没有缓存，一般发生在刚上床的时候，心率数据可靠，呼吸数据计算不可靠，那么用 4:1方式进行重新计算
        if (healthData.getHeartRate() >= 0 && healthData.getHeartRate() <= 100) {
            Double heartBreathRate = (Double) QueueUtil.getHeartBreathRate(deviceNo);
            if (heartBreathRate == null) {
                heartBreathRate = DEFFAULT_HEART_BREATH_REATE;
            }
            breathValue = (int) Math.round(healthData.getHeartRate() / heartBreathRate);
            healthData.setBreathValue(breathValue);
            healthData.setBreathValueWeight(healthData.getHeartRateWeight());
            healthData.setReallyData(healthData.getReallyData() + 3);
            logger.info("cal breath through heartrate :" + healthData);
        }

    }

    public void setMeddoHealthDataDao(MeddoHealthDataDao meddoHealthDataDao) {
        this.meddoHealthDataDao = meddoHealthDataDao;
    }
}








