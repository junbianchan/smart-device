package co.darma.smartmattress.service;

import co.darma.smartmattress.ccb.AccessTokenManager;
import co.darma.smartmattress.ccb.entity.AccessContext;
import co.darma.smartmattress.entity.ErrorResponse;
import co.darma.smartmattress.util.ErrorMessageBuilder;
import co.darma.smartmattress.util.LogUtil;
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
 * Created by frank on 15/11/17.
 */
@Component
@Path("token")
public class TokenService {

    private final static String USER_NAME = "user_name";

    private final static String ACCESS_TOKEN = "access_token";

    private final static String REFRESH_TOKNE = "refresh_token";

    private final static String EXPIRE_IN = "expires_in";

    private final static String TOKEN_TYPE = "token_type";

    private final static String REFRESH_INTERFACE = "token_refresh";

    public static final String AUTHORIZE_TIME = "authorize_time";

    @Autowired
    private AccessTokenManager manager;

    /**
     * 用于格式化数据
     */
    private static ObjectMapper mapper = new ObjectMapper();

    private static Logger logger = Logger.getLogger(TokenService.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String refreshToken(String request,
                               @HeaderParam("v") String version) {

        try {
            JsonNode node = mapper.readTree(request);
            String grantType = node.get("grant_type").asText();
            String accessToken = node.get("access_token").asText();
            String scope = node.get("scope").asText();

            logger.info("accessToken is :" + accessToken + ", grantType :" + grantType);

            if (StringUtils.equals(grantType, "refresh_token")) {
                AccessContext context = manager.refreshToken(accessToken);

                if (context == null) {
                    return ErrorMessageBuilder.buildErrorMessage(ErrorResponse.CLIENT_ERROR, "Argument invalid."
                            , ErrorResponse.ACCESS_DENIED, "access_token is invalid or expired.");
                }
                Map<String, Object> result = new HashMap<String, Object>();
                result.put(USER_NAME, context.getUserName());
                result.put(ACCESS_TOKEN, accessToken);
                result.put(REFRESH_TOKNE, context.getAccessToken());
                result.put(EXPIRE_IN, context.getExpiresIn());
                result.put(TOKEN_TYPE, context.getTokenType());
                result.put(AUTHORIZE_TIME, context.getCreateTime());
                result.put(USER_NAME, context.getUserName());

                Map<String, Object> finalMap = new HashMap<String, Object>();
                finalMap.put(REFRESH_INTERFACE, result);
                return mapper.writeValueAsString(finalMap);

            } else {

                return ErrorMessageBuilder.buildErrorMessage(ErrorResponse.CLIENT_ERROR, "Argument invalid."
                        , ErrorResponse.ARGUMENT_ILLEAGEL, "[grant_type] should be refresh_token.");

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


//
//@DefaultValue("refresh_token") @FormParam("grant_type") String grantType,
//@FormParam("access_token") String acessToken,
//@DefaultValue("default") @FormParam("scope") String scope