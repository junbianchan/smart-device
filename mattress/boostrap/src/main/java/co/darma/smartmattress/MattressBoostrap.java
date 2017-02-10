package co.darma.smartmattress;

import co.darma.smartmattress.analysis.handle.HistoryPacketHandleService;
import co.darma.smartmattress.analysis.handle.StateChangeHandleService;
import co.darma.smartmattress.analysis.appdemo.DemoSocketServer;
import co.darma.smartmattress.parser.connect.MattressSocketServer;
import co.darma.smartmattress.queue.ServiceProcessor;
import co.darma.smartmattress.upgrade.entity.UpgradeJob;
import co.darma.smartmattress.upgrade.spiimpl.UpgradePacketCheckServiceImpl;
import co.darma.smartmattress.upgrade.util.TaskExcutor;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by frank on 15/10/19.
 */
public class MattressBoostrap implements ServletContextListener {

    private static Logger logger = Logger.getLogger(MattressBoostrap.class);

    private ServiceProcessor healthDataProcessor;

    private ServiceProcessor stateDataProcessor;

    private ServiceProcessor upgradePacketCheckService;


    public void contextInitialized(ServletContextEvent servletContextEvent) {

        logger.info("Begin to initial the app server...");

        MattressSocketServer server = new MattressSocketServer();
        server.setDaemon(true);
        server.start();

        healthDataProcessor = new ServiceProcessor();
        healthDataProcessor.register(HistoryPacketHandleService.class, 4);
        healthDataProcessor.run();

        stateDataProcessor = new ServiceProcessor();
        stateDataProcessor.register(StateChangeHandleService.class, 3);
        stateDataProcessor.run();

        logger.info("Starting....");

        DemoSocketServer demoSocketServer = new DemoSocketServer();
        demoSocketServer.setDaemon(true);
        demoSocketServer.start();

//        TaskExcutor.startFixTimeTask();

        String path = MattressBoostrap.class.getResource("/").getPath();
        logger.info("Base path is :" + path);
        if (StringUtils.isNoneBlank(path)) {
            UpgradeJob.setSystemBasePath(path);
        }

        upgradePacketCheckService = new ServiceProcessor();
        upgradePacketCheckService.register(UpgradePacketCheckServiceImpl.class, 1);
        upgradePacketCheckService.run();

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

        logger.info("Stop the system....");

    }


}
