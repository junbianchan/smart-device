package co.darma.smartmattress.validate;

import co.darma.smartmattress.entity.ErrorResponse;
import co.darma.smartmattress.util.ErrorMessageBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Created by frank on 15/10/29.
 */
public class ManHealthValidator {

    private static final Logger logger = Logger.getLogger(ManHealthValidator.class);

    public static final int DEVICE_NO_LENGTH = 512;

    public static final String CLIENT_ERROR_MESSAGE = "Client Arguments illegal.";

    public static Map<String,ErrorResponse> validateArgument(Map<String, Object> argumenet) {

        logger.info("validating the argument ..." + argumenet);
        String deviceNo = (String) argumenet.get("deviceNo");

        if (StringUtils.isEmpty(deviceNo)) {
            return ErrorMessageBuilder.buildErrorResponse(ErrorResponse.CLIENT_ERROR, CLIENT_ERROR_MESSAGE,
                    ErrorResponse.ARGUMENT_ILLEAGEL, "deviceNo cannot be null.");
        }
        if (deviceNo.length() > DEVICE_NO_LENGTH || !deviceNo.matches("[A-Za-z0-9_]+")) {
            return ErrorMessageBuilder.buildErrorResponse(ErrorResponse.CLIENT_ERROR, CLIENT_ERROR_MESSAGE,
                    ErrorResponse.ARGUMENT_ILLEAGEL,
                    "deviceNo's length should be less than 512 Characters and consist of only alphabet,Digital or underline.");
        }

//        String userName = (String) argumenet.get("userName");
//        if (StringUtils.isEmpty(userName)) {
//            return ErrorMessageBuilder.buildErrorResponse(ErrorResponse.CLIENT_ERROR, CLIENT_ERROR_MESSAGE,
//                    ErrorResponse.ARGUMENT_ILLEAGEL, "userName cannot be null.");
//        }

//        if (userName.length() > DEVICE_NO_LENGTH) {
//            return ErrorMessageBuilder.buildErrorResponse(ErrorResponse.CLIENT_ERROR, CLIENT_ERROR_MESSAGE,
//                    ErrorResponse.ARGUMENT_ILLEAGEL, "userName's length should be less than 512 Characters.");
//        }

        Long startTime = (Long) argumenet.get("startTime");
        Long endTime = (Long) argumenet.get("endTime");
        if(startTime == null || endTime == null)
        {
            return ErrorMessageBuilder.buildErrorResponse(ErrorResponse.CLIENT_ERROR, CLIENT_ERROR_MESSAGE,
                    ErrorResponse.ARGUMENT_ILLEAGEL, "startTime or endTime cannot be null.");
        }

        if (startTime < 0 || endTime < 0 || endTime < startTime) {
            return ErrorMessageBuilder.buildErrorResponse(ErrorResponse.CLIENT_ERROR, CLIENT_ERROR_MESSAGE,
                    ErrorResponse.ARGUMENT_ILLEAGEL, "startTime or endTime less than 0 or endTime less than startTime.");
        }
        return null;
    }
}
