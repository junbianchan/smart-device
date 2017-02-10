package co.darma.smartmattress.analysis.dao.impl;

import co.darma.smartmattress.analysis.dao.BodyMotionDao;
import co.darma.smartmattress.analysis.entity.BodyMotion;
import co.darma.smartmattress.util.ArgumentHandleUtil;
import co.darma.smartmattress.database.DataAccess;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by frank on 15/10/23.
 */
public class BodyMotionDaoImpl implements BodyMotionDao {

    private static Logger logger = Logger.getLogger(BodyMotionDaoImpl.class);

    @Override
    public void saveOrUpdateBodyMotionList(List<BodyMotion> bodyMotionList) {
        if (CollectionUtils.isNotEmpty(bodyMotionList)) {
            dataAcessor.batchSaveObject("co.darma.smartmattress.batchInsertBM", bodyMotionList);
        } else {
            logger.error("BodyMotion is null");
        }
    }

    @Override
    public List<BodyMotion> queryBodyMotionWithTime(Integer projectId, Long startTime, Long endTime) {
        Map<String, Object> maps = new HashMap<String, Object>(2);
        maps.put("projectId", projectId);
        maps.put("startTime", startTime);
        maps.put("endTime", endTime);
        List<BodyMotion> bodyMotionList = dataAcessor.queryList("co.darma.smartmattress.getBodyMotionWithTime", maps);
        return bodyMotionList;
    }

    @Override
    public BodyMotion queryBodyMotionWithDeviceIdAndTime(Integer deviceId, Long startTime, Long endTime) {
        Map<String, Object> maps = new HashMap<String, Object>(2);
        maps.put("deviceId", deviceId);
        maps.put("startTime", startTime);
        maps.put("endTime", endTime);
        List<BodyMotion> result = dataAcessor.queryList("co.darma.smartmattress.queryBodyMotionWithDeviceAndTime", maps);
        if (CollectionUtils.isNotEmpty(result)) {
            return result.get(result.size() - 1);
        }

        return null;
    }


    private DataAccess dataAcessor;

    public void setDataAcessor(DataAccess dataAcessor) {
        this.dataAcessor = dataAcessor;
    }
}


//    @Override
//    public List<BodyMotion> queryBodyMotionWithTime(String userName, String deviceNo, Long startTime, Long endTime) {
//        Map<String, Object> maps = new HashMap<String, Object>(4);
//        maps.put("deviceNo", deviceNo);
//        maps.put("startTime", startTime);
//        maps.put("endTime", endTime);
//        return dataAcessor.queryList("co.darma.smartmattress.queryBodyMotionWithTime", maps);
//    }
//
//    @Override
//    public List<BodyMotion> batchQueryBodyMotionWithId(Long startIndex, Integer count) {
//        if (startIndex < 0 || count < 0) {
//            return null;
//        }
//        return dataAcessor.queryList("co.darma.smartmattress.batchQueryBodyMotionWithId",
//                ArgumentHandleUtil.buildArgument("startIndex", startIndex, "count", count));
//    }
