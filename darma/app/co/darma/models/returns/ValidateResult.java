package co.darma.models.returns;

/**
 * 校验结果
 * Created by frank on 15/11/18.
 */
public class ValidateResult {

    private int errorCode;

    private String errorMessage;

    public ValidateResult(int code, String errorMessage) {
        this.errorCode = code;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[errorCode:").append(errorCode).append(",errorMessage:").append(errorMessage).append("]");
        return sb.toString();
    }
}
