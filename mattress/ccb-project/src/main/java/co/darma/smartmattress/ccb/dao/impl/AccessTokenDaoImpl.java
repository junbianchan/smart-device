package co.darma.smartmattress.ccb.dao.impl;

import co.darma.smartmattress.ccb.dao.AccessTokenDao;
import co.darma.smartmattress.ccb.entity.AccessContext;
import co.darma.smartmattress.constant.MybatisConstant;
import co.darma.smartmattress.database.DataAccess;
import co.darma.smartmattress.util.ArgumentHandleUtil;

import java.util.List;

/**
 * Created by frank on 15/11/15.
 */
public class AccessTokenDaoImpl implements AccessTokenDao {

    private DataAccess dataAcessor;

    public void setDataAcessor(DataAccess dataAcessor) {
        this.dataAcessor = dataAcessor;
    }


    @Override
    public void updateAccessToken(AccessContext context) {
        dataAcessor.saveOrUpdateObject(MybatisConstant.NAMESPACE + "insertAccessContext", context);

    }

    @Override
    public List<AccessContext> queryActiveAccessContextByUserName(String userName) {
        return dataAcessor.queryList(MybatisConstant.NAMESPACE + "queryACByUserName",
                ArgumentHandleUtil.buildArgument("userName", userName));
    }

    @Override
    public List<AccessContext> queryActiveAccessContext(String accessToken) {
        return dataAcessor.queryList(MybatisConstant.NAMESPACE + "queryACByToken",
                ArgumentHandleUtil.buildArgument("accessToken", accessToken));
    }
}
