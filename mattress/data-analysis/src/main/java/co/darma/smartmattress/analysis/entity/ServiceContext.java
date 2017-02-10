package co.darma.smartmattress.analysis.entity;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by frank on 15/10/23.
 */
public class ServiceContext {

    private Map<String, Object> contextMap = new HashMap<String, Object>();


    /**
     * 加入参数
     *
     * @param name
     * @param object
     */
    public void putArgument(String name, Object object) {

        if (StringUtils.isNoneBlank(name)) {
            contextMap.put(name, object);
        } else {
            throw new IllegalArgumentException("Put Argument into context ,name is should not be empty");
        }
    }

    /**
     * 通过参数名获取参数值。
     */
    public Object getArgByName(String name) {

        return contextMap.get(name);
    }

    /**
     * 判断是否为空
     *
     * @param context
     * @return
     */
    public static boolean isEmpty(ServiceContext context) {
        if (context != null && MapUtils.isNotEmpty(context.contextMap)) {
            return false;
        }
        return true;
    }
}
