package co.darma.smartmattress.util;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * Created by frank on 15/10/26.
 */
public class BeanManagementUtil {

    private static ApplicationContext smartmattressContext = ContextLoader.getCurrentWebApplicationContext();

    /**
     * 通过bean名字获得bean实例
     *
     * @param beanName
     * @return
     */
    public static Object getBeanByName(String beanName) {
        return smartmattressContext.getBean(beanName);
    }

    /**
     * 通过类型获取bean
     *
     * @param classType
     * @return
     */
    public static <T>T getBeanByType(Class<T> classType) {

        return smartmattressContext.getBean(classType);
    }
}
