package co.darma.smartmattress.service;

import co.darma.smartmattress.analysis.dao.BodyMotionDao;
import co.darma.smartmattress.analysis.dao.MeddoHealthDataDao;
import co.darma.smartmattress.ccb.AccessTokenManager;
import co.darma.smartmattress.ccb.entity.AccessContext;
import co.darma.smartmattress.dao.UserDao;
import co.darma.smartmattress.dao.impl.UserDaoImpl;
import co.darma.smartmattress.entity.ErrorResponse;
import co.darma.smartmattress.entity.UserInfo;
import co.darma.smartmattress.util.BeanManagementUtil;
import co.darma.smartmattress.util.ErrorMessageBuilder;
import co.darma.smartmattress.util.HealthDataUtil;
import co.darma.smartmattress.util.LogUtil;
import co.darma.smartmattress.validate.ManDeviceBoundValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by frank on 15/11/25.
 */
@Component
@Path("physicaldata")
public class BatchQueryHealthDataWithTimeServie {

    public static final String CURRENT_TIME = "current_time";
    public static final String MOTION = "motion";
    @Autowired
    private AccessTokenManager manager;

    @Autowired
    protected MeddoHealthDataDao meddoHealthDataDao;

    @Autowired
    private UserDao userDao;

    private static Logger logger = Logger.getLogger(BatchQueryHealthDataWithTimeServie.class);

    private static final String INTERFACE = "physical_data";

    private static final String HEALTH_DATA = "health_data";

    public BatchQueryHealthDataWithTimeServie() {

    }

    /**
     * 用于格式化数据
     */
    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private BodyMotionDao bodyMotionDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String bathQueryHealthData(
            @HeaderParam("access_token") String accessToken,
            @HeaderParam("v") String version,
            @QueryParam("start_time") Long startTime,
            @QueryParam("end_time") Long endTime,
            @QueryParam("is_debug") String debug,
            @QueryParam("device_ids") String deviceIds
    ) {

        logger.info("call argument [startTime :" + startTime + ",end_time: " + endTime);
        AccessContext context = manager.authByToken(accessToken);
        Long startTimeQ = System.currentTimeMillis();
        if (context == null) {
            logger.error(" accessToken is invalid :" + accessToken);
            return ErrorMessageBuilder.buildErrorMessage(ErrorResponse.ACCESS_DENIED,
                    "access_token is invalid or time out.",
                    ErrorResponse.ACCESS_DENIED, "access_token is invalid or expired.");
        }

        UserInfo userInfo = userDao.queryUserByUserName(context.getUserName());
        if (userInfo == null) {
            logger.error(" userInfo is invalid :" + context.getUserName());
            return ErrorMessageBuilder.buildErrorMessage(ErrorResponse.ACCESS_DENIED,
                    "access_token is invalid or time out.",
                    ErrorResponse.ACCESS_DENIED, "access_token is invalid or expired.");
        }

        Long currentTimeMillis = System.currentTimeMillis();
        if (startTime == null || endTime == null || startTime < 0 || endTime < 0 || startTime > endTime || endTime > (currentTimeMillis) / 1000 + 18000) {
            return ErrorMessageBuilder.buildErrorMessage(ErrorResponse.CLIENT_ERROR,
                    ManDeviceBoundValidator.CLIENT_ERROR_MESSAGE,
                    ErrorResponse.ARGUMENT_ILLEAGEL, "argument [start_time] or [end_time] is invalid.");
        }

        Map<String, Object> healthData = new HashMap<String, Object>(2);

        try {

            Integer projectId = userInfo.getProject().getProjectId();

            Map<String, Object> healthDataMap = HealthDataUtil.buildHealthDataNew(meddoHealthDataDao.queryMeddoHealthDataWithTime(projectId, startTime, endTime), debug, deviceIds);

            healthData.put(HEALTH_DATA, healthDataMap);

            Map<String, Object> motion = HealthDataUtil.buildHealthData(bodyMotionDao.queryBodyMotionWithTime(projectId, startTime, endTime), debug, deviceIds);

            healthData.put(MOTION, motion);

            Map<String, Object> finalMap = new HashMap<String, Object>(2);
            finalMap.put(INTERFACE, healthData);

            finalMap.put(CURRENT_TIME, System.currentTimeMillis() / 1000);

            String result = mapper.writeValueAsString(finalMap);

            if (logger.isDebugEnabled()) {
                logger.info("data is :" + result);
            }
            logger.info("cost time is :" + (System.currentTimeMillis() - startTimeQ));
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error(LogUtil.logException(e));
            return ErrorMessageBuilder.buildErrorMessage(ErrorResponse.SERVER_ERROR, "server error.",
                    ErrorResponse.PASE_JSON_ERROR, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogUtil.logException(e));
            return ErrorMessageBuilder.buildErrorMessage(ErrorResponse.SERVER_ERROR, "server error.",
                    ErrorResponse.SERVER_UNKOWN_ERROR, "Unkown Error.");
        }

    }
}
