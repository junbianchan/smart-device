package co.darma.common;

import play.mvc.Http;

import play.Logger;

/**
 * Created by frank on 15/11/20.
 */
public class AgentAnalysisor {

    public static final String APP = "app";

    public static String paseToGetAgent(Http.Request request) {
        Logger.info("agent is :" + request.getHeader("agent"));
        return APP;
    }
}
