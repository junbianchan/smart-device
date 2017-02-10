package co.darma.smartmattress.queue;

import co.darma.smartmattress.service.Service;
import co.darma.smartmattress.util.BeanManagementUtil;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 15/10/12.
 */
public class ServiceProcessor {

    private static Logger logger = Logger.getLogger(ServiceProcessor.class);

    private static final int MAX_SERVICE_JOB = 128;

    private List<Service> serviceJobList = null;

    public ServiceProcessor() {
        serviceJobList = new LinkedList<Service>();
        logger.info("serviceJobList is :" + serviceJobList);
    }

    /**
     * 注册类型为Service的服务serviceNum个
     *
     * @param serviceClass
     * @param serviceNum
     */
    public <T extends Service> void register(Class<T> serviceClass, int serviceNum) {

        logger.info("register service " + serviceNum);

        if (serviceNum <= 0 || (serviceNum + serviceJobList.size()) > MAX_SERVICE_JOB) {
            throw new IllegalArgumentException("Argument 'serviceNum' is invalid " +
                    ",make sure the max job num less than or equals to " + MAX_SERVICE_JOB + " and more than 0.");
        }

        if (serviceClass == null) {
            throw new IllegalArgumentException("serviceClass cannot be null");
        }


        for (int i = 0; i < serviceNum; ++i) {
            try {
                Service service = BeanManagementUtil.getBeanByType(serviceClass);
                logger.info("service is :" + service);
                serviceJobList.add(service);
                logger.info("serviceJobList:" + serviceJobList);
                logger.info("serviceJobList,size:" + serviceJobList.size());

            } catch (Exception e) {
                logger.error("Error When get the bean  of service :" + serviceClass.toString());
            }

        }

    }

    public void run() {
        logger.info("Running the service ,num:" + serviceJobList.size());
        for (Service service : serviceJobList) {
            service.start();
        }
        logger.info("Running service Done!");

    }

}
