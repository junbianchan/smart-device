package co.darma.smartmattress.upgrade.entity;

/**
 * Created by frank on 15/10/29.
 */
public class ErrorResponse {

    public static final String ErrorResponse = "error_response";

    public static final int CLIENT_ERROR = 40000;

    public static final int ARGUMENT_ILLEAGEL = 40005;

    public static final int ACCESS_DENIED = 40003;

    public static final int RESOURCE_NOT_FOUND = 40004;

    public static final int TASK_EXCEED = 40005;

    public static final int SERVER_ERROR = 50000;

    private int code;

    private String message;

    private int sub_code;

    private String sub_message;


    public ErrorResponse(int code, String message, int subCode, String subMessage) {
        this.code = code;
        this.message = message;
        this.sub_code = subCode;
        this.sub_message = subMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSub_code() {
        return sub_code;
    }

    public void setSub_code(int sub_code) {
        this.sub_code = sub_code;
    }

    public String getSub_message() {
        return sub_message;
    }

    public void setSub_message(String sub_message) {
        this.sub_message = sub_message;
    }
}

