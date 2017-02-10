package co.darma.smartmattress.analysis.dao.impl;

import co.darma.smartmattress.analysis.dao.ManBehaviorDao;
import co.darma.smartmattress.analysis.entity.ManBehavior;
import co.darma.smartmattress.database.DataAccess;
import co.darma.smartmattress.util.ArgumentHandleUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by frank on 15/11/4.
 */
public class ManBehaviorDaoImpl implements ManBehaviorDao {

    private static Logger logger = Logger.getLogger(ManBehaviorDaoImpl.class);


    private DataAccess dataAcessor;

    @Override
    public void saveManBehavior(ManBehavior behavior) {

        if (behavior != null) {
            dataAcessor.saveOrUpdateObject("co.darma.smartmattress.insertBehavior", behavior);
        } else {
            logger.error("behavior is null.");
        }
    }

    @Override
    public ManBehavior queryLastestManBehaviorByDevice(Integer deviceId, Long limitTime) {
        if (deviceId != null) {
            List<ManBehavior> resultList = dataAcessor.queryList("co.darma.smartmattress.queryLastestManBehaviorByDevice",
                    ArgumentHandleUtil.buildArgument("deviceId", deviceId, "limitTime", limitTime));
            if (CollectionUtils.isNotEmpty(resultList)) {
                return resultList.get(0);
            }
        } else {
            logger.error("behavior is null.");
        }
        return null;
    }

    @Override
    public ManBehavior queryLastBehavior(Integer deviceId) {
        if (deviceId != null) {
            List<ManBehavior> resultList = dataAcessor.queryList("co.darma.smartmattress.queryLastestBehaviorByDevice",
                    ArgumentHandleUtil.buildArgument("deviceId", deviceId));
            if (CollectionUtils.isNotEmpty(resultList)) {
                return resultList.get(0);
            }
        } else {
            logger.error("behavior is null.");
        }
        return null;

    }


    public void setDataAcessor(DataAccess dataAcessor) {
        this.dataAcessor = dataAcessor;
    }
}
