package co.darma.smartmattress.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by frank on 15/11/12.
 */
public class ArgumentHandleUtil {

    /**
     * 该函数回家参数双数下表的值作为key，偶数下标对应的值作为Value放到map里面。
     *
     * @param argAndValue
     * @return
     */
    public static Map<Object, Object> buildArgument(Object... argAndValue) {

        Map<Object, Object> returnMap = new HashMap<Object, Object>();

        if (argAndValue == null) {
            return returnMap;
        }


        for (int i = 0; i + 1 < argAndValue.length; i += 2) {
            returnMap.put(argAndValue[i], argAndValue[i + 1]);
        }

        return returnMap;
    }
}
