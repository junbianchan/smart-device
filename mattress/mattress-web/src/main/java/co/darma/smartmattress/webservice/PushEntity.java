package co.darma.smartmattress.webservice;

import co.darma.smartmattress.ccb.util.TokenIdGenerator;
import co.darma.smartmattress.util.DataFormateUtil;
import co.darma.smartmattress.util.LogUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by frank on 15/11/18.
 */
public class PushEntity {


    private static Logger logger = Logger.getLogger(PushEntity.class);

    public PushEntity(Map<String, Object> argMap) {

        String UUID = null;
        UUID = (String) argMap.get("deviceNo");
        this.UUID = UUID;

        SensorData = new LinkedList<PushEntity.SensorData>();
        PushEntity.SensorData sdata = this.new SensorData();
        String propertyCode = (String) argMap.get("PropertyCode");
        sdata.setPropertyCode(propertyCode);

        List<Map<String, String>> dataList = new LinkedList<Map<String, String>>();

        Map<String, String> tmpDataMap = new HashMap<String, String>();

        Long time = (Long) argMap.get("timeStamp");
        tmpDataMap.put("At", DataFormateUtil.toDate(time));

        String type = (String) argMap.get("type");

        if (StringUtils.equals("3", type)) {
            //离床
            tmpDataMap.put("Value", "1");
        } else if (StringUtils.equals("4", type)) {
            //上床
            tmpDataMap.put("Value", "0");
        }

        dataList.add(tmpDataMap);
        sdata.setData(dataList);
        SensorData.add(sdata);
    }

    class SensorData {

        public SensorData() {
        }

        private String PropertyCode;

        private List<Map<String, String>> Data;

        @JsonProperty("PropertyCode")
        public String getPropertyCode() {
            return PropertyCode;
        }

        public void setPropertyCode(String propertyCode) {
            PropertyCode = propertyCode;
        }

        @JsonProperty("Data")
        public List<Map<String, String>> getData() {
            return Data;
        }

        public void setData(List<Map<String, String>> data) {
            Data = data;
        }
    }

    private String UUID;

    private List<PushEntity.SensorData> SensorData;

    @JsonProperty("UUID")
    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    @JsonProperty("SensorData")
    public List<PushEntity.SensorData> getSensorData() {
        return SensorData;
    }

    public void setSensorData(List<PushEntity.SensorData> sensorData) {
        SensorData = sensorData;
    }

    static ObjectMapper mapper = new ObjectMapper();

    public String toJson() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

//    public static void main(String[] args) throws JsonProcessingException {
//
//        PushEntity e = new PushEntity();
//
//        e.setUUID("test");
//
//
//        List<PushEntity.SensorData> datas = new LinkedList<PushEntity.SensorData>();
//
//
//        PushEntity.SensorData data = e.new SensorData();
//
//        data.setPropertyCode("2");
//
//        List<Map<String, String>> dataList = new LinkedList<Map<String, String>>();
//
//        Map<String, String> maps = new HashMap<String, String>();
//
//        maps.put("At", "test");
//        maps.put("Value", "121212");
//
//        dataList.add(maps);
//
//        data.setData(dataList);
//
//        datas.add(data);
//
//
//        e.setSensorData(datas);
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        System.out.printf(mapper.writeValueAsString(e));
//
//
//    }


}
