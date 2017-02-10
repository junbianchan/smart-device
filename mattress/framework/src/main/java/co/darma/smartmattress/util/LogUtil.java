package co.darma.smartmattress.util;

/**
 * Created by frank on 15/11/4.
 */
public class LogUtil {
    public static String logException(Exception e) {
        if (e != null) {
            StringBuilder log = new StringBuilder();
            if (e.getStackTrace() != null) {
                log.append(e.getStackTrace()[0]);
            }
            log.append("\t").append(e.getMessage());
            return log.toString();

        }
        return "";
    }

    public static String traceException(Exception e) {
        if (e != null) {
            StringBuilder log = new StringBuilder();
            StackTraceElement[] elements = e.getStackTrace();
            for (StackTraceElement se : elements) {
                log.append("\r\n\t\t").append(se);
            }
            log.append("\r\n\t\t").append(e.getMessage());
            return log.toString();
        }
        return "";
    }
}
