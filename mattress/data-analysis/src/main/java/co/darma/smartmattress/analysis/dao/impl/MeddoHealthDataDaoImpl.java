package co.darma.smartmattress.analysis.dao.impl;

import co.darma.smartmattress.analysis.dao.MeddoHealthDataDao;
import co.darma.smartmattress.analysis.entity.MeddoHealthData;
import co.darma.smartmattress.database.DataAccess;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by frank on 15/11/25.
 */
public class MeddoHealthDataDaoImpl implements MeddoHealthDataDao {

    private DataAccess dataAcessor;

    private static final Logger logger = Logger.getLogger(MeddoHealthDataDaoImpl.class);

    @Override
    public void saveMeddoHealthData(List<MeddoHealthData> datasList) {
        for (MeddoHealthData data : datasList) {
            saveMeddoHealthData(data);
        }
    }

    @Override
    public void saveMeddoHealthData(MeddoHealthData data) {
        Map<String, Object> params = new HashMap<String, Object>(2);
        params.put("deviceId", data.getDevice().getId());
        params.put("markTime", data.getMarkTime());
        if (!dataAcessor.existOrNot("co.darma.smartmattress.queryObjExistOrNot", params)) {
            dataAcessor.saveOrUpdateObject("co.darma.smartmattress.updateHealthDatas", data);
        } else {
            logger.error(" healthData exist : " + data);
        }
    }

    @Override
    public List<MeddoHealthData> queryMeddoHealthDataWithTime(Integer projectId, Long startTime, Long endTime) {
        Map<String, Object> maps = new HashMap<String, Object>(2);
        maps.put("projectId", projectId);
        maps.put("startTime", startTime * 1000);
        maps.put("endTime", endTime * 1000);
        List<MeddoHealthData> result = dataAcessor.queryList("co.darma.smartmattress.queryHealthDatas", maps);
        return result;
    }

    @Override
    public MeddoHealthData queryLastestHealthDataByDevice(Integer deviceId, Long startTime) {
        Map<String, Object> maps = new HashMap<String, Object>(2);
        maps.put("deviceId", deviceId);
        maps.put("startTime", startTime);
        List<MeddoHealthData> result = dataAcessor.queryList("co.darma.smartmattress.queryLastestHealthDataByDevice", maps);
        if (CollectionUtils.isNotEmpty(result)) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public List<MeddoHealthData> queryHealthDataByDevice(Integer deviceId, Long startTime, Long endTime) {
        Map<String, Object> maps = new HashMap<String, Object>(2);
        maps.put("deviceId", deviceId);
        maps.put("startTime", startTime);
        maps.put("endTime", endTime);
        List<MeddoHealthData> result = dataAcessor.queryList("co.darma.smartmattress.queryHealthDataByDevice", maps);
        return result;
    }


    public void setDataAcessor(DataAccess dataAcessor) {
        this.dataAcessor = dataAcessor;
    }
}
