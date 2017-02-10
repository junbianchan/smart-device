package co.darma.smartmattress.client;

import org.glassfish.jersey.client.JerseyClient;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;

/**
 * 用于向meddo请求服务
 * Created by frank on 15/11/9.
 */
public class MeddoWSClient {


    private String baseUrl;


    public MeddoWSClient() {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(baseUrl);

//        target.request().buildPost();
    }

}
