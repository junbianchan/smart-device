package co.darma.smartmattress.analysis.util;

/**
 * Created by frank on 15/12/15.
 */
public class DuplicatePacketException extends RuntimeException {

    public DuplicatePacketException(String errorMessage) {
        super(errorMessage);
    }

    public DuplicatePacketException(Exception e) {
        super(e);
    }
}
