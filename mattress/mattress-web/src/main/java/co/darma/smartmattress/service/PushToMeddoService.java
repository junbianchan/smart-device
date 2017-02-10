package co.darma.smartmattress.service;

import co.darma.smartmattress.analysis.entity.PushContextEntity;
import co.darma.smartmattress.analysis.spi.PushToClient;
import co.darma.smartmattress.util.LogUtil;
import co.darma.smartmattress.webservice.CallBack;
import co.darma.smartmattress.webservice.Interface;
import co.darma.smartmattress.webservice.ServiceClient;
import co.darma.smartmattress.webservice.ServiceResult;
import org.apache.log4j.Logger;

/**
 * Created by frank on 15/11/18.
 */
public class PushToMeddoService implements PushToClient {

    private Interface interfaceInfo;

    private static Logger logger = Logger.getLogger(PushToMeddoService.class);

    @Override
    public boolean pushStatePacket(PushContextEntity message) {

        logger.info("Push message to Meddo Corporation,message:" + message);

        ServiceClient serviceClient = ServiceClient.getInstance();

        int tryTime = -1;

        while (!serviceClient.isSessionValid()) {
            // 重新连接

            while (tryTime < 10) {

                logger.info("try to signIn to Service Server :"
                        + serviceClient.getConf() + ",times:" + tryTime);

                tryTime++;
                if (!serviceClient.signIn(null)) {
                    //登录失败，再次尝试
                    continue;
                } else {
                    break;
                }
            }

            if (tryTime >= 9) {
                logger.error("try to signIn ,but failed and exit.");
                return false;
            }

        }

        try {
            serviceClient.sendMessage(interfaceInfo, message.toMap(), true,
                    new CallBack() {
                        @Override
                        public void process(ServiceResult result) {
                            logger.info(result);
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(LogUtil.logException(e));
        }

        return false;
    }

    public Interface getInterfaceInfo() {
        return interfaceInfo;
    }

    public void setInterfaceInfo(Interface interfaceInfo) {
        this.interfaceInfo = interfaceInfo;
    }
}
