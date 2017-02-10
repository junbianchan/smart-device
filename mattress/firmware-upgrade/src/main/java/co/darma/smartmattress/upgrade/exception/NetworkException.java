package co.darma.smartmattress.upgrade.exception;

/**
 * Created by frank on 15/12/29.
 */
public class NetworkException extends RuntimeException {
    public NetworkException(String errorMessage) {
        super(errorMessage);
    }
}
