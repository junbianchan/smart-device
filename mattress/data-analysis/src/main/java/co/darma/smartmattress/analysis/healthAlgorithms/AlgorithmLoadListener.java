package co.darma.smartmattress.analysis.healthAlgorithms;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by frank on 15/10/20.
 */
public class AlgorithmLoadListener implements ServletContextListener {

    private static Logger logger = Logger.getLogger(AlgorithmLoadListener.class);

    private static String BASEPATH = "basePath";

    public void contextInitialized(ServletContextEvent servletContextEvent) {

        logger.info("begin loading libtest.so.....");

        String path = servletContextEvent.getServletContext().getInitParameter(BASEPATH);
        try {
            System.load(path + "/algorithmlib.so");

        } catch (Exception e) {
            logger.error(e.getStackTrace());
        }

        logger.info("loaded Done.");
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    public void setBasePath(String basePath) {
        this.BASEPATH = basePath;
    }
}
