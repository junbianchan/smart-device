package co.darma.smartmattress.webservice;

/**
 * Created by frank on 15/11/18.
 */
public class ServiceResult {

    private Object resultMessage;

    public Object getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(Object resultMessage) {
        this.resultMessage = resultMessage;
    }

    @Override
    public String toString() {
        return "ServiceResult{" +
                "resultMessage=" + resultMessage +
                '}';
    }
}
