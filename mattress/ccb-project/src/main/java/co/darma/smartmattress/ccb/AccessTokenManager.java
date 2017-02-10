package co.darma.smartmattress.ccb;

import co.darma.smartmattress.ccb.dao.AccessTokenDao;
import co.darma.smartmattress.ccb.entity.AccessContext;
import co.darma.smartmattress.ccb.util.TokenIdGenerator;
import co.darma.smartmattress.dao.UserDao;
import co.darma.smartmattress.encrypt.SHA2EncryptUtil;
import co.darma.smartmattress.entity.UserInfo;
import co.darma.smartmattress.util.LogUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by frank on 15/11/13.
 */
public class AccessTokenManager {

    private static Long DEFAULTEXPIRESIN = 2 * 24 * 60 * 60L;

    private UserDao userDao;

    private AccessTokenDao accessTokenDao;

    /**
     * 用于token的验证
     */
    private static Map<String, AccessContext> tokenMap = new ConcurrentHashMap<String, AccessContext>();


    private static Logger logger = Logger.getLogger(AccessTokenManager.class);

    /**
     * 授权
     *
     * @param userName
     * @param password
     * @return null表示用户名或者密码错误
     * @throws Exception
     */
    public AccessContext authByUserNameAndPass(String userName, String password) throws Exception {
        logger.info("userName login :" + userName);
        try {
            if (StringUtils.isNotBlank(userName)) {
                UserInfo user = userDao.queryUserByUserName(userName);
                if (user == null) {
                    logger.error("There is no user :" + userName);
                    return null;
                }
                String salt = user.getSalt();
                String decrptedPass = SHA2EncryptUtil.encrypt(password, salt);
                if (!StringUtils.equals(user.getPassword(), decrptedPass)) {
                    return null;
                }

                AccessContext context = new AccessContext();
                context.setUserName(user.getUserName());
                context.setAccessToken(TokenIdGenerator.tokenGenerate());
                context.setExpiresIn(DEFAULTEXPIRESIN);
                context.setTokenType(user.getTokenType());
                context.setCreateTime((System.currentTimeMillis() / 1000));
                //刷新缓存和数据库中的context
                refreshToken(context);
                return context;

            }
        } catch (Exception e) {
            logger.error(LogUtil.logException(e));
        }
        return null;

    }

    /**
     * 刷新
     *
     * @param srcContext
     */
    private void refreshToken(AccessContext srcContext) {
        String userName = srcContext.getUserName();
        List<AccessContext> contexts = accessTokenDao.queryActiveAccessContextByUserName(userName);
        AccessContext oldContext = null;
        if (CollectionUtils.isNotEmpty(contexts)) {
            oldContext = contexts.get(0);
        }

        //不是首次登陆，更新token
        if (oldContext != null) {
            oldContext.setCreateTime(srcContext.getCreateTime());
            oldContext.setAccessToken(srcContext.getAccessToken());
            oldContext.setTokenType(srcContext.getTokenType());
            accessTokenDao.updateAccessToken(oldContext);
        } else {
            //保存本次的context
            accessTokenDao.updateAccessToken(srcContext);
        }
        tokenMap.put(srcContext.getUserName(), srcContext);
    }


    /**
     * 刷新token
     *
     * @param oldAccessToken
     * @return 如果为空，表示token无效或者失效，需要重新登录
     */
    public AccessContext refreshToken(String oldAccessToken) throws NoSuchAlgorithmException {

        //TODO
        logger.info("refreshToken is : " + oldAccessToken);
        AccessContext context = tokenMap.get(oldAccessToken);

        if (context == null) {
            List<AccessContext> cl = accessTokenDao.queryActiveAccessContext(oldAccessToken);
            if (CollectionUtils.isNotEmpty(cl)) {
                context = cl.get(0);
            } else {
                return null;
            }
        }
        context.setAccessToken(TokenIdGenerator.tokenGenerate());
        context.setCreateTime((System.currentTimeMillis() / 1000));
        accessTokenDao.updateAccessToken(context);
        tokenMap.put(context.getAccessToken(), context);
        return context;

    }

    /**
     * 授权接口
     *
     * @param accessToken
     * @return 返回null表示token不正确或者过期
     */
    public AccessContext authByToken(String accessToken) {
        //TODO
        logger.info("token is : " + accessToken);

        if (StringUtils.isEmpty(accessToken)) {
            return null;
        }
        AccessContext context = tokenMap.get(accessToken);

        //环境里面没有，就到数据库查询
        if (context == null) {
            List<AccessContext> cl = accessTokenDao.queryActiveAccessContext(accessToken);
            if (CollectionUtils.isNotEmpty(cl)) {
                context = cl.get(0);
            }
        }

        if (context != null) {
            Long currentTime = System.currentTimeMillis() / 1000;
            if ((context.getCreateTime() + context.getExpiresIn()) < currentTime) {
                //Timeout
                context.unAcitve();
                tokenMap.remove(context.getAccessToken());
                accessTokenDao.updateAccessToken(context);
                return null;
            }
            return context;
        } else {
            // 没有找到，那么就是过期了
            return null;
        }

    }


    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public AccessTokenDao getAccessTokenDao() {
        return accessTokenDao;
    }

    public void setAccessTokenDao(AccessTokenDao accessTokenDao) {
        this.accessTokenDao = accessTokenDao;
    }
}
