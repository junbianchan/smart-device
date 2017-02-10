package co.darma.smartmattress.util;

import co.darma.smartmattress.analysis.entity.MeddoHealthData;
import co.darma.smartmattress.analysis.entity.MeddoValue;
import co.darma.smartmattress.entity.MeddoMetaData;
import co.darma.smartmattress.entity.MeddoMetaDataDebugMode;
import co.darma.smartmattress.entity.ValueAndTime;
import co.darma.smartmattress.entity.ValueAndTimeDebugMode;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by frank on 15/11/17.
 */
public class HealthDataUtil {

    public static final String DEVICE_ID = "device_id";

    public static final String DATA_LIST = "data_list";

    public static final String LAST_INDEX = "last_index";

    public static final String DATA_INFOS = "data_infos";


    /**
     * 构建返回值
     *
     * @param srcDataList
     * @return
     * @throws JsonProcessingException
     */
    public static Map<String, Object> buildHealthData(List<?> srcDataList, String isDebug,String deviceIds) throws JsonProcessingException {
        if (CollectionUtils.isNotEmpty(srcDataList)) {

            List deviceIdList = null;
            if (StringUtils.equals(isDebug, "true") && StringUtils.isNotEmpty(deviceIds)) {
                String[] deviceIdArray = deviceIds.split(",");
                deviceIdList = Arrays.asList(deviceIdArray);
                Iterator<?> healthDataIterator = srcDataList.iterator();
                while (healthDataIterator.hasNext()) {
                    MeddoValue data = (MeddoValue)healthDataIterator.next();
                    if (!deviceIdList.contains(data.getGroupKey())) {
                        healthDataIterator.remove();
                    }
                }
            }

            Integer maxIndex = 0;
            List<Map<String, Object>> finalDataInfo = new LinkedList<Map<String, Object>>();
            String deviceNo = null;
            List<ValueAndTime> valueAndTimeList = null;
            Map<String, Object> valueMap = null;
            MeddoValue obj = null;

            for (int i = 0; i < srcDataList.size(); ) {
                if (srcDataList.get(i) == null) {
                    ++i;
                    continue;
                }
                obj = (MeddoValue) srcDataList.get(i);
                maxIndex = (obj.getId() > maxIndex) ? obj.getId() : maxIndex;
                if (deviceNo == null) {
                    deviceNo = obj.getGroupKey();
                    valueMap = new HashMap<String, Object>(2);
                    valueMap.put(DEVICE_ID, deviceNo);
                    valueAndTimeList = new LinkedList<ValueAndTime>();
                    if (StringUtils.equalsIgnoreCase("true", isDebug)) {
                        valueAndTimeList.add(new ValueAndTimeDebugMode(obj));
                    } else {
                        valueAndTimeList.add(new ValueAndTime(obj));
                    }
                    valueMap.put(DATA_LIST, valueAndTimeList);
                    finalDataInfo.add(valueMap);
                    //next element
                    i++;
                    continue;
                }

                if (deviceNo.equals(obj.getGroupKey())) {
                    if (StringUtils.equalsIgnoreCase("true", isDebug)) {
                        valueAndTimeList.add(new ValueAndTimeDebugMode(obj));
                    } else {
                        valueAndTimeList.add(new ValueAndTime(obj));
                    }
                    ++i;
                } else {
                    deviceNo = null;
                }
            }
            Map<String, Object> finalMap = new HashMap<String, Object>(2);
//            finalMap.put(LAST_INDEX, maxIndex);
            finalMap.put(DATA_INFOS, finalDataInfo);
            return finalMap;
        } else {
            return null;
        }
    }


    public static Map<String, Object> buildHealthDataNew(List<MeddoHealthData> healthDataList, String debugMode, String deviceIds) throws JsonProcessingException {
        if (CollectionUtils.isNotEmpty(healthDataList)) {

            List deviceIdList = null;
            if (StringUtils.equals(debugMode, "true") && StringUtils.isNotEmpty(deviceIds)) {
                String[] deviceIdArray = deviceIds.split(",");
                deviceIdList = Arrays.asList(deviceIdArray);
                Iterator<MeddoHealthData> healthDataIterator = healthDataList.iterator();
                while (healthDataIterator.hasNext()) {
                    MeddoHealthData data = healthDataIterator.next();
                    if (!deviceIdList.contains(data.getDevice().getDeviceNo())) {
                        healthDataIterator.remove();
                    }
                }
            }

            Integer maxIndex = 0;
            List<Map<String, Object>> finalDataInfo = new LinkedList<Map<String, Object>>();
            String deviceNo = null;
            List<MeddoMetaData> valueAndTimeList = null;
            Map<String, Object> valueMap = null;
            MeddoHealthData obj = null;

            for (int i = 0; i < healthDataList.size(); ) {
                if (healthDataList.get(i) == null) {
                    ++i;
                    continue;
                }
                obj = (MeddoHealthData) healthDataList.get(i);
                maxIndex = (obj.getId() > maxIndex) ? obj.getId() : maxIndex;
                if (deviceNo == null) {
                    deviceNo = obj.getDevice().getDeviceNo();
                    valueMap = new HashMap<String, Object>(2);
                    valueMap.put(DEVICE_ID, deviceNo);
                    valueAndTimeList = new LinkedList<MeddoMetaData>();
                    if (StringUtils.equalsIgnoreCase("true", debugMode)) {
                        valueAndTimeList.add(new MeddoMetaDataDebugMode(obj));
                    } else {
                        valueAndTimeList.add(new MeddoMetaData(obj));
                    }
                    valueMap.put(DATA_LIST, valueAndTimeList);
                    finalDataInfo.add(valueMap);
                    //next element
                    i++;
                    continue;
                }

                if (deviceNo.equals(obj.getDevice().getDeviceNo())) {
                    if (StringUtils.equalsIgnoreCase("true", debugMode)) {
                        valueAndTimeList.add(new MeddoMetaDataDebugMode(obj));
                    } else {
                        valueAndTimeList.add(new MeddoMetaData(obj));
                    }
                    ++i;
                } else {
                    deviceNo = null;
                }
            }
            Map<String, Object> finalMap = new HashMap<String, Object>(2);
            finalMap.put(DATA_INFOS, finalDataInfo);
            return finalMap;
        } else {
            return null;
        }

    }

}
