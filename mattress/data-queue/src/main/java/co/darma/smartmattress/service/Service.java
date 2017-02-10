package co.darma.smartmattress.service;

import org.apache.log4j.Logger;

/**
 * 无会话处理服务
 * Created by frank on 15/10/12.
 */
public abstract class Service extends Thread {

    private static Logger logger = Logger.getLogger(Service.class);

    private volatile boolean keepRunning = true;

    public abstract void execute();

    public void stopRunning() {
        keepRunning = false;
    }

    @Override
    public void run() {
        logger.info("Service Started... name:" + Thread.currentThread().getName());
        while (keepRunning) {
            try {
                execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
