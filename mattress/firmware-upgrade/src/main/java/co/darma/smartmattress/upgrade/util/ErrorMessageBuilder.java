package co.darma.smartmattress.upgrade.util;

import co.darma.smartmattress.upgrade.entity.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by frank on 15/10/29.
 */
public class ErrorMessageBuilder {

    private static Logger logger = Logger.getLogger(ErrorMessageBuilder.class);

    private static ObjectMapper mapper = new ObjectMapper();

    public static ErrorResponse buildErrorResponse(int code, String message, int subCode, String subMessage) {
        return new ErrorResponse(code, message, subCode, subMessage);
    }


    public static String buildErrorMessage(int code, String message, int subCode, String subMessage) {
        ErrorResponse errorResponse = new ErrorResponse(code, message, subCode, subMessage);
        return getErrorMessage(errorResponse);
    }


    public static String getErrorMessage(ErrorResponse errorResponse) {
        Map<String, Object> error = new HashMap<String, Object>(1);
        error.put(ErrorResponse.ErrorResponse, errorResponse);
        String result = null;
        try {
            result = mapper.writeValueAsString(error);
        } catch (JsonProcessingException e) {
            logger.error(e);
        }
        return result;
    }

}
