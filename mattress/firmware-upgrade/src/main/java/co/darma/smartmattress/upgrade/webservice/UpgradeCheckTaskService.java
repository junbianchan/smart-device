package co.darma.smartmattress.upgrade.webservice;

import co.darma.smartmattress.ccb.AccessTokenManager;
import co.darma.smartmattress.ccb.entity.AccessContext;
import co.darma.smartmattress.upgrade.entity.ErrorResponse;
import co.darma.smartmattress.upgrade.util.ErrorMessageBuilder;
import co.darma.smartmattress.upgrade.util.TaskExcutor;
import co.darma.smartmattress.upgrade.util.UpgradeCheckTask;
import co.darma.smartmattress.util.LogUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新建一个检查的任务，检查队列中时候有请求任务
 * Created by frank on 15/12/30.
 */
@Component
@Path("upgrade")
public class UpgradeCheckTaskService {

    private static final String INTERFACE = "upgradechecker";

    private static Logger logger = Logger.getLogger(UpgradeCheckTaskService.class);

    @Autowired
    private AccessTokenManager manager;

    public UpgradeCheckTaskService() {
        logger.info("successfull register...");
    }

    /**
     * 用于格式化数据
     */
    private static ObjectMapper mapper = new ObjectMapper();


    /**
     * 新增检查任务
     *
     * @return
     */
    @POST
    @Path("checker")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewUpgradeChecker(
            @HeaderParam("access_token") String accessToken,
            @HeaderParam("v") String version
    ) {

        if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(version)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    ErrorMessageBuilder.buildErrorResponse(ErrorResponse.ACCESS_DENIED,
                            "access_token is invalid or time out.",
                            ErrorResponse.ACCESS_DENIED, "access_token or v is null.")
            ).build();
        }

        AccessContext context = manager.authByToken(accessToken);
        logger.info("context is :" + context);
        if (context == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    ErrorMessageBuilder.buildErrorResponse(ErrorResponse.ACCESS_DENIED,
                            "access_token is invalid or time out.",
                            ErrorResponse.ACCESS_DENIED, "access_token is invalid or expired.")
            ).build();
        }

        boolean result;
        try {
            //先清理，后台新增
            TaskExcutor.cleanUnRunningChecker();

            result = TaskExcutor.addTask();
            if (result) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(INTERFACE, result);
                return Response.status(Response.Status.OK).entity(mapper.writeValueAsString(map)).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(
                        ErrorMessageBuilder.buildErrorResponse(ErrorResponse.CLIENT_ERROR,
                                "",
                                ErrorResponse.TASK_EXCEED, "Too much task running.")
                ).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    ErrorMessageBuilder.buildErrorResponse(ErrorResponse.SERVER_ERROR,
                            "",
                            ErrorResponse.SERVER_ERROR, "Error.")
            ).build();
        }

    }

    @GET
    @Path("checker")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUpgradeChecker(
            @HeaderParam("access_token") String accessToken,
            @HeaderParam("v") String version
    ) {
        List<UpgradeCheckTask> resultList = TaskExcutor.getCheckerList();
        if (CollectionUtils.isNotEmpty(resultList)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(INTERFACE, resultList);
            return Response.status(Response.Status.OK).entity(map).build();
        }
        return Response.status(Response.Status.OK).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteUnUsedChecker() {

        TaskExcutor.cleanUnRunningChecker();

        return "";
    }

}
