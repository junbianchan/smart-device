package co.darma.smartmattress.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by frank on 15/11/19.
 */
public class DataFormateUtil {

    private static Long onSecond = 1000L;

    private static TimeZone UTC = TimeZone.getTimeZone("CTT");

    /**
     * 转换成时间
     *
     * @param timeInSecond
     * @return
     */
    public static String toDate(Long timeInSecond) {
        if (timeInSecond != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(UTC);
            java.util.Date dt = new Date(timeInSecond * onSecond);
            String sDateTime = sdf.format(dt);
            return sDateTime;
        }
        return null;
    }


//    public static void main(String[] args) {
//        System.out.println("time = [" + System.currentTimeMillis() + "]");
//        System.out.println("args = [" + DataFormateUtil.toDate(System.currentTimeMillis() / onSecond) + "]");
//    }
}
