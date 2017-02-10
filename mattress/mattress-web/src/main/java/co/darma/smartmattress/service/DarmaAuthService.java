package co.darma.smartmattress.service;

import co.darma.smartmattress.ccb.AccessTokenManager;
import co.darma.smartmattress.ccb.entity.AccessContext;
import co.darma.smartmattress.entity.ErrorResponse;
import co.darma.smartmattress.util.ErrorMessageBuilder;
import co.darma.smartmattress.util.LogUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by frank on 15/11/13.
 */
@Component
@Path("authorize")
public class DarmaAuthService {

    @Autowired
    private AccessTokenManager manager;

    private final static String USER_NAME = "user_name";

    private final static String ACCESS_TOKEN = "access_token";

    private final static String REFRESH_TOKNE = "refresh_token";

    private final static String EXPIRE_IN = "expires_in";

    private final static String TOKEN_TYPE = "token_type";

    private final static String INTERFACE_NAME = "authorize";

    public static final String AUTHORIZE_TIME = "authorize_time";

    /**
     * 用于格式化数据
     */
    private static ObjectMapper mapper = new ObjectMapper();

    private static Logger logger = Logger.getLogger(DarmaAuthService.class);

    @Context
    HttpServletRequest request;


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String auth(String request,
                       @HeaderParam("v") String version) {

        try {
            JsonNode node = mapper.readTree(request);
            String grantType = node.get("grant_type").asText();
            String userName = node.get("user_name").asText();
            String password = node.get("password").asText();
            String scope = node.get("scope").asText();
            logger.info("auth argument :" + grantType + "," + userName + scope);

            if (StringUtils.equals(grantType, "password")) {
                AccessContext context = manager.authByUserNameAndPass(userName, password);

                if (context == null) {
                    return ErrorMessageBuilder.buildErrorMessage(ErrorResponse.CLIENT_ERROR, "Argument invalid."
                            , ErrorResponse.ACCESS_DENIED, "user_name or password is invalid.");
                }
                Map<String, Object> result = new HashMap<String, Object>();
                result.put(USER_NAME, context.getUserName());
                result.put(ACCESS_TOKEN, context.getAccessToken());
                result.put(EXPIRE_IN, context.getExpiresIn());
                result.put(TOKEN_TYPE, context.getTokenType());
                result.put(AUTHORIZE_TIME, context.getCreateTime());
                result.put(USER_NAME, context.getUserName());

                Map<String, Object> finalMap = new HashMap<String, Object>();
                finalMap.put(INTERFACE_NAME, result);
                return mapper.writeValueAsString(finalMap);


            } else {
                logger.error("grantType is wrong :" + grantType);
                return ErrorMessageBuilder.buildErrorMessage(ErrorResponse.CLIENT_ERROR, "Argument invalid."
                        , ErrorResponse.ARGUMENT_ILLEAGEL, "[grant_type] now just susport [password].");

            }
        } catch (IOException e) {
            logger.error(LogUtil.logException(e));
            return ErrorMessageBuilder.buildErrorMessage(ErrorResponse.ARGUMENT_ILLEAGEL, "Argument invalid."
                    , ErrorResponse.ARGUMENT_ILLEAGEL, "Request Argument should be json format");
        } catch (Exception e) {
            logger.error(LogUtil.logException(e));
            return ErrorMessageBuilder.buildErrorMessage(ErrorResponse.SERVER_ERROR, "Server error."
                    , ErrorResponse.SERVER_UNKOWN_ERROR, "System Exception");

        }

    }


}
