package co.darma.smartmattress.dao.impl;

import co.darma.smartmattress.constant.MybatisConstant;
import co.darma.smartmattress.dao.UserDao;
import co.darma.smartmattress.database.DataAccess;
import co.darma.smartmattress.entity.UserInfo;
import org.apache.log4j.Logger;

/**
 * Created by frank on 15/10/28.
 */
public class UserDaoImpl implements UserDao {


    private static Logger logger = Logger.getLogger(UserDaoImpl.class);

    private DataAccess dataAcessor;

    public UserDaoImpl() {
        logger.info("UserDaoImpl init...");
    }

    @Override
    public UserInfo queryUserByUserName(String userName) {
        return (UserInfo) dataAcessor.queryObjectByObject(MybatisConstant.NAMESPACE + "queryUserInfoByUserName", userName);
    }

    @Override
    public boolean insertUserInfo(UserInfo userInfo) {
        return dataAcessor.saveOrUpdateObject(MybatisConstant.NAMESPACE + "insertNewUser", userInfo);
    }

    @Override
    public UserInfo queryUserInfoById(Integer userId) {
        return (UserInfo) dataAcessor.queryObjectByObject(MybatisConstant.NAMESPACE + "queryUserInfoByUserId", userId);
    }

    public void setDataAcessor(DataAccess dataAcessor) {
        this.dataAcessor = dataAcessor;
    }
}
