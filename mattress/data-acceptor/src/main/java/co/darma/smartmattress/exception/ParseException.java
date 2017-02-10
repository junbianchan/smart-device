package co.darma.smartmattress.exception;

/**
 * Created by frank on 15/10/29.
 */
public class ParseException extends Exception {

    public ParseException(String message) {
        super(message);
    }

    public ParseException(Exception e) {
        super(e);
    }

}
