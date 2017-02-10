package co.darma.common;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by frank on 15/12/11.
 */
public class StringUtil {

    /**
     * string转换成时间
     *
     * @param str
     * @return
     */
    public static Long strToLong(String str) {
        if (StringUtils.isNumeric(str)) {
            return Long.valueOf(str);
        }
        return null;
    }


}
