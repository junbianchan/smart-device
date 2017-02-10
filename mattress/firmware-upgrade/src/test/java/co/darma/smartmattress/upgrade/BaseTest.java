package co.darma.smartmattress.upgrade;

import co.darma.smartmattress.util.BeanManagementUtil;
import mockit.Mock;
import mockit.MockUp;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by frank on 15/12/30.
 */
public class BaseTest {

    private static ClassPathXmlApplicationContext context;

    static {
        context = new ClassPathXmlApplicationContext(
                "spring/applicationContext_acceptor.xml",
                "spring/applicationContext-upgrade.xml",
                "spring/applicationContext_ibatis.xml",
                "spring/applicationContext_ccb.xml"
        );

        MockUp mu3 = new MockUp<BeanManagementUtil>() {
            @Mock
            public Object getBeanByName(String beanName) {
                return getContext().getBean(beanName);
            }

            @Mock
            public <T> T getBeanByType(Class<T> classType) {
                return getContext().getBean(classType);
            }
        };


    }

    public static ClassPathXmlApplicationContext getContext() {
        return context;
    }
}
