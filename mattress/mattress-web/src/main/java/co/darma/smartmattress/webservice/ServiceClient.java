package co.darma.smartmattress.webservice;

import co.darma.smartmattress.util.BeanManagementUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by frank on 15/11/18.
 */
public class ServiceClient {

    public static final String ACCESS_TOKEN_KEY = "AccessToken";
    private ServerConfiguration conf;

    private ServiceSession serviceSession;

    private static Logger logger = Logger.getLogger(ServiceClient.class);

    private static ExecutorService fixThreadThreadPool = Executors.newFixedThreadPool(4);


    /**
     * 会话是否过期
     *
     * @return
     */
    public boolean isSessionValid() {
        if (serviceSession != null) {
            return !serviceSession.isExpired();
        }
        return false;
    }

    public boolean signIn(ServerConfiguration conf) {
        if (conf != null) {
            this.conf = conf;
        }

        if (this.conf != null) {
            Client client = ClientBuilder.newClient();
            String baseUrl = this.conf.getBaseUrl();
            String method = this.conf.getAuthAddress();

            String userName = this.conf.getUserName();
            String password = this.conf.getPassword();

            String reqUrl = baseUrl + method;
            WebTarget target = client.target(reqUrl);

            serviceSession = new ServiceSession();
            serviceSession.setExpireIn(-1L);
            serviceSession.setTokenUpdateTime(System.currentTimeMillis() / 1000);
            serviceSession.setToken("D#sf-82Xf2-f2DSF-xF2%j2");
            return true;
        }
        return false;

    }

    public boolean refreshToken() {
        return true;
    }

    public static ServiceClient getInstance() {

        return (ServiceClient) BeanManagementUtil.getBeanByName("serviceClient");

    }

    public ServiceResult sendMessage(final Interface interfaceInfo, final Map<String, Object> arguments,
                                     boolean ansync,
                                     final CallBack callBack) throws Exception {

        Client client = ClientBuilder.newClient();
        String baseUrl = conf.getConnectType() + "://" + conf.getBaseUrl();
        String method = interfaceInfo.getInterfaceInfo();
        String reqUrl = baseUrl + method;
        final WebTarget target = client.target(reqUrl);
        if (ansync) {
            //异步
            fixThreadThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    logger.info("Send ansync ...");
                    Invocation invocation = null;
                    String argument = new PushEntity(arguments).toJson();
                    logger.info("Ready to sending Message to Meddo json is :" + argument);
                    if (StringUtils.equalsIgnoreCase("POST", interfaceInfo.getRequestType())) {
                        Invocation.Builder builder = target.request();
                        builder.header(ACCESS_TOKEN_KEY, serviceSession.getToken());
                        invocation = builder.buildPost(
                                Entity.entity(argument, MediaType.APPLICATION_JSON_TYPE)
                        );
                    }
                    Response response = invocation.invoke();
                    String result = response.readEntity(String.class);
                    logger.info("Result from meddo is :" + result);
                    response.close();
                    ServiceResult resultEntity = new ServiceResult();
                    resultEntity.setResultMessage(result);
                    callBack.process(resultEntity);
                }
            });
        } else {
            Invocation invocation = null;
            String argument = new PushEntity(arguments).toJson();
            logger.info("Ready to sending Message to Meddo json is :" + argument);

            if (StringUtils.equalsIgnoreCase("POST", interfaceInfo.getRequestType())) {
                Invocation.Builder builder = target.request();
                builder.header(ACCESS_TOKEN_KEY, serviceSession.getToken());
                invocation = builder.buildPost(
                        Entity.entity(argument, MediaType.APPLICATION_JSON_TYPE)
                );
            }
            Response response = invocation.invoke();
            String result = response.readEntity(String.class);
            logger.info("Result from meddo is :" + result);

            response.close();
        }
        return null;
    }


    public ServerConfiguration getConf() {
        return conf;
    }

    public void setConf(ServerConfiguration conf) {
        this.conf = conf;
    }

    public ServiceSession getServiceSession() {
        return serviceSession;
    }

    public void setServiceSession(ServiceSession serviceSession) {
        this.serviceSession = serviceSession;
    }
}
