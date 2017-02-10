package co.darma.smartmattress.database;

import co.darma.smartmattress.util.LogUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.mybatis.spring.MyBatisSystemException;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;
import java.util.Objects;

/**
 * 保存数据或者查询数据实体类。
 * <p/>
 * <p/>
 * Created by frank on 15/10/26.
 */
public class DataAccess<T extends Object> {

    private SqlSessionTemplate sqlSession;

    private static Logger logger = Logger.getLogger(DataAccess.class);

    /**
     * 保存实体
     * 如果保存成功，那么返回true;
     * 如果保存失败，那么返回false;
     * 该方法内，会记录错误日志。
     *
     * @param id
     * @param entity
     * @return
     */
    public boolean saveOrUpdateObject(String id, T entity) {

        if (entity == null) {
            logger.error("The Entity is null");
            return false;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Begin to save or update the paramter ,class type is :" + entity.getClass());
        }
        sqlSession.insert(id, entity);
        return true;
    }

    public boolean batchSaveObject(String idStr, List<T> entities) {

        if (CollectionUtils.isEmpty(entities)) {
            logger.error("The entities is null");
            return false;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Begin to save or update the parameter ,class type is :" + entities.get(0).getClass());
        }
        sqlSession.insert(idStr, entities);
        return true;
    }


    public Object queryObjectByObject(String idString, Object paramterValue) {
        try {
            return sqlSession.selectOne(idString, paramterValue);
        } catch (MyBatisSystemException e) {
            logger.error(LogUtil.traceException(e));
            return null;
        }
    }

    public List<T> queryList(String id, Object paramValue) {
        try {
            return sqlSession.selectList(id, paramValue);
        } catch (MyBatisSystemException e) {
            logger.error(LogUtil.traceException(e));
            return null;
        }
    }

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    /**
     * 判断该元素在不在数据库中
     *
     * @param id
     * @param paramValue
     * @return 存在返回True，不存在返回False
     */
    public boolean existOrNot(String id, Object paramValue) {

        try {
            List<T> result = sqlSession.selectList(id, paramValue);
            if (result != null && result.size() > 0) {
                return true;
            }
        } catch (MyBatisSystemException e) {
            logger.error(LogUtil.traceException(e));
        }
        return false;
    }


    public int deleteObject(String idString, Object paramValue) {
        return sqlSession.delete(idString, paramValue);
    }
}
