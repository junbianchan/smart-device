package co.darma.smartmattress;

import co.darma.smartmattress.entity.DeviceCookie;
import co.darma.smartmattress.entity.Element;
import co.darma.smartmattress.exception.SystemException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by frank on 15/10/12.
 */
public class QueueUtil {

    public static int CACHE_PACKET_LENGTH = 120;

    private static Logger logger = Logger.getLogger(QueueUtil.class);

    public static int HISTORY_PACKET_NUMBER = 60;

    public static int HISTORY_PACKET_AT_LEAST_NUMBER = 30;

    private static long ONE_SECOND = 1000;

    private static Map<String, List<Element>> historeDataMap = new HashMap<String, List<Element>>();

    private static BlockingQueue<Element> stateDataQueue = new LinkedBlockingQueue<Element>();

    private static final Lock historeLock = new ReentrantLock();

//    private static final Condition historeDataQueue_is_full = historeLock.newCondition();

//    private static final Condition historeDataQueue_is_empty = historeLock.newCondition();

    /**
     * 每个设备一上线，都会维持一个状态
     */
    private static final Map<String, DeviceCookie> SESSION_CACHE = new ConcurrentHashMap<String, DeviceCookie>(128);

    /*
    * 该元素的作用是，当historeDataMap中，设备对应包列表中，包含该元素时，说明需要处理该包了。
     */
    private static final Element FORCE_ELEMENT = new Element() {
        @Override
        public int hashCode() {
            return super.hashCode();
        }
    };

//    /**
//     * 将包放到队列中。
//     * <p/>
//     * 改队列用户保存实时的报数据
//     *
//     * @param packet  包
//     * @param timeout 超时时间，时间为妙
//     * @throws InterruptedException
//     */
//    public static void putHistoryElement(String devicdId, Element packet, Long timeout) throws InterruptedException {
//
//        try {
//            historeLock.lock();
//            timeout = timeout * ONE_SECOND;
//            long lastTime = System.currentTimeMillis();
//            long current = lastTime;
//
//            List<Element> dataList = historeDataMap.get(devicdId);
//
//            while (timeout > 0 && CollectionUtils.isNotEmpty(dataList)
//                    && dataList.size() >= HISTORY_PACKET_NUMBER) {
//                //队列已经满了，等待别人取
//                historeDataQueue_is_empty.await(timeout, TimeUnit.MILLISECONDS);
//                current = System.currentTimeMillis();
//                timeout = timeout - (current - lastTime);
//                lastTime = current;
//
//                dataList = historeDataMap.get(devicdId);
//            }
//
//            //为了防止服务端太久，若是超时了，就强制塞到队列
//            dataList = (dataList == null ? new LinkedList<Element>() : dataList);
//            dataList.add(packet);
//            historeDataMap.put(devicdId, dataList);
//
//            if (dataList.size() >= HISTORY_PACKET_NUMBER || packet == FORCE_ELEMENT) {
//                historeDataQueue_is_full.signalAll();
//            }
//
//            logger.info("Put device:" + devicdId + " packet to cache.");
//        } finally {
//            historeLock.unlock();
//        }
//
//    }

//    public static List<Element> takeHistoryElement() throws SystemException {
//        try {
//            historeLock.lock();
//
//            List<Element> targetDataList = null;
//            String targetKey = null;
//            while (targetDataList == null) {
//                Set<Map.Entry<String, List<Element>>> dataSets = historeDataMap.entrySet();
//                for (Map.Entry<String, List<Element>> elements : dataSets) {
//                    if (elements.getValue().size() >= HISTORY_PACKET_NUMBER
//                            || elements.getValue().get(elements.getValue().size() - 1) == FORCE_ELEMENT) {
//                        targetDataList = elements.getValue();
//                        targetKey = elements.getKey();
//                        targetDataList.remove(FORCE_ELEMENT);
////                        targetDataList.remove(targetDataList.size() -1);
//                        break;
//                    }
//                }
//                // 没有满足的。
//                if (targetDataList == null) {
//                    historeDataQueue_is_full.await();// wait here...
//                }
//            }
//            //将要处理的数据从队列中取出。
//            if (targetKey != null) {
//                historeDataMap.remove(targetKey);
//            }
//            historeDataQueue_is_empty.signalAll();
//
//            logger.info("Take divice: " + targetKey + ".  packets to handle:");
//            return targetDataList;
//
//        } catch (InterruptedException e) {
//            throw new SystemException(e);
//        } finally {
//            historeLock.unlock();
//        }
//    }


    /**
     * 原始的正常光纤数据
     */
    private static BlockingQueue<List<Element>> historyDataQueue = new LinkedBlockingQueue<List<Element>>();


    /**
     * 缓存上报上来的数据
     *
     * @param deviceId
     * @param packet
     * @return
     */
    public static List<Element> cacheHistoryElement(String deviceId, Element packet) {
        try {
            //这里如果有性能问题建议按照设备ID来分开锁
            historeLock.lock();
            List<Element> storeElement = historeDataMap.get(deviceId);

            if (storeElement == null) {
                storeElement = new LinkedList<Element>();
                storeElement.add(packet);
                historeDataMap.put(deviceId, storeElement);
                return null;
            } else if (storeElement.size() >= (HISTORY_PACKET_NUMBER - 1)) {
                storeElement.add(packet);
                //清除出来
                return historeDataMap.remove(deviceId);
            } else {
                storeElement.add(packet);
                return null;
            }
        } finally {
            historeLock.unlock();
        }
    }

    /**
     * 将缓存数据取出
     *
     * @param deviceId
     * @return
     */
    public static List<Element> popHisotryElement(String deviceId) {
        try {
            historeLock.lock();
            return historeDataMap.remove(deviceId);
        } finally {
            historeLock.unlock();
        }
    }

    /**
     * 每HISTORY_PACKET_NUMBER 个数据会分装到该任务队列中
     *
     * @param elements
     * @throws SystemException
     */
    public static void putHistoryElementsToQueue(List<Element> elements) throws SystemException {
        try {
            if (elements != null) {
                historyDataQueue.put(elements);
            }
        } catch (InterruptedException e) {
            throw new SystemException(e);
        }
    }

    /**
     * 从任务队列中，获取出来计算
     *
     * @return
     * @throws SystemException
     */
    public static List<Element> takeHistoryElementFromQueue() throws SystemException {
        try {
            return historyDataQueue.take();
        } catch (InterruptedException e) {
            throw new SystemException(e);
        }
    }


    /**
     * 插入一个状态包
     *
     * @param packet
     */
    public static void addStatePacket(Element packet) throws SystemException {
        try {
            stateDataQueue.put(packet);
        } catch (InterruptedException e) {
            throw new SystemException(e);
        }
    }

    /**
     * 获取一个状态包
     *
     * @return
     * @throws SystemException
     */
    public static Element takeStatePacket() throws SystemException {
        try {
            return stateDataQueue.take();
        } catch (InterruptedException e) {
            throw new SystemException(e);
        }
    }


    private static Map<String, Object> cacheLastPacketList = new ConcurrentHashMap<String, Object>();

    /**
     * 上次缓存数据，
     * 这个数据用于，当当前分钟计算不出来的话，就用上次缓存的数据来替换
     *
     * @param key
     * @return
     */
    public static Object getLastPacketBy(String key) {
        return cacheLastPacketList.get(key);
    }


    /**
     * 缓存上次的数据
     *
     * @param key
     * @param object
     */
    public static void cachePacket(String key, Object object) {
        cacheLastPacketList.put(key, object);
    }

    /**
     * 清理上3分钟的缓存
     *
     * @param key
     */
    public static void clearCache(String key) {
        cacheLastPacketList.remove(key);
    }


    /**
     * 心率/呼吸的值
     */
    private static Map<String, Object> HEAR_BREATH_RATE = new ConcurrentHashMap<String, Object>();


    public static void updateHeartBreathRate(String deviceNo, Object heartBreathRate) {
        HEAR_BREATH_RATE.put(deviceNo, heartBreathRate);
    }

    public static Object getHeartBreathRate(String DeviceNo) {
        return HEAR_BREATH_RATE.get(DeviceNo);
    }

    public static DeviceCookie getDeviceCookie(String deviceNo) {
        return SESSION_CACHE.get(deviceNo);
    }

    public static void updateDeviceCookie(DeviceCookie deviceCookie) {

        if (deviceCookie == null || deviceCookie.getDeviceNo() == null) {
            return;
        }
        String deviceNo = deviceCookie.getDeviceNo();
        if (StringUtils.isNoneEmpty(deviceNo)) {
            SESSION_CACHE.put(deviceNo, deviceCookie);
        }
    }

    /**
     * 删除Cookie,一般是离线的状况
     *
     * @param deviceNo
     */
    public static void removeDeviceCookie(String deviceNo) {
        if (StringUtils.isNoneEmpty(deviceNo)) {
            SESSION_CACHE.remove(deviceNo);
        }
    }


    /***
     * 缓存同步时间包，用于判断该设备是否需要升级
     */
    private static BlockingQueue<Element> waitForUpgradeQueue = new LinkedBlockingQueue<Element>();


    private static final int TWO_SECOND = 2;

    /**
     * 更新时间的包，会放到改队列中，然后有另外一个程序会从改队列中获取包，然后判断改设备是否需要升级。
     *
     * @param packet 时间包
     */
    public static void putToUpgradeQueue(Element packet) throws SystemException {
        try {
            if (packet != null) {
                waitForUpgradeQueue.offer(packet, TWO_SECOND, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

    /**
     * 从该队列中获取出包，然后判断出该设备是否需要升级
     *
     * @return
     * @throws SystemException
     */
    public static Element takePacketFromUpgradeQueue() throws SystemException {
        try {
            return waitForUpgradeQueue.take();
        } catch (InterruptedException e) {
            throw new SystemException(e);
        }
    }

    /**
     * 缓存上次的包
     */
    private static final Map<String, Object> HALF_PACKET_CACHE = new ConcurrentHashMap<String, Object>(128);


    public static Object popHalfPacket(String deviceNo) {
        return HALF_PACKET_CACHE.remove(deviceNo);
    }

    public static void putHalfPacketToCache(String deviceNo, Object packet) {
        if (StringUtils.isNotEmpty(deviceNo)) {
            HALF_PACKET_CACHE.put(deviceNo, packet);
        }
    }


    /**
     * 升级包的ACK包（响应）
     */
    private static BlockingQueue<Element> upgradeResult = new LinkedBlockingQueue<Element>();


    /**
     * 拿出升级ACK包
     *
     * @param timeout
     * @return
     * @throws InterruptedException
     */
    public static Element takeUpgradeResult(int timeout) throws InterruptedException {
        return upgradeResult.poll(timeout, TimeUnit.SECONDS);
    }


    /**
     * 将升级包ACK放到缓存中
     *
     * @param element
     * @return
     */
    public static void putUpgradeResultPacket(Element element) {
        upgradeResult.add(element);
    }

}
