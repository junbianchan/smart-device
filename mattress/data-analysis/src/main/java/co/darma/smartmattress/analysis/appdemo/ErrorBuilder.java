package co.darma.smartmattress.analysis.appdemo;

import co.darma.smartmattress.analysis.entity.PushContextEntity;

/**
 * Created by frank on 15/11/5.
 */
public class ErrorBuilder {

    public static final String DARMA = "Darma;";

    public static final String END = ";End";

    public static final int CLIENT_ERROR = 40000;

    public static final int ARGUMENT_ILLEAGEL = 40005;

    public static final int ACCESS_DENIED = 40003;

    public static final int RESOURCE_NOT_FOUND = 40004;

    public static final int SERVER_ERROR = 50000;


    public static String buildErrorMessage(int errorCode, String errorMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append(DARMA);
        sb.append("Error:").append(errorCode).append(";ErrorMessage:").append(errorMessage);
        sb.append(END);
        return sb.toString();
    }

    public static String buildSucess(String deviceNo, String username) {
        StringBuilder sb = new StringBuilder();
        sb.append(DARMA)
                .append("Cmd:LoginSucessfull;")
                .append("Type:login;DeviceNo:")
                .append(deviceNo).append(";UserName:")
                .append(username).append(END);
        return sb.toString();
    }

    public static String buildResultByContext(PushContextEntity message) {
        StringBuilder sb = new StringBuilder();

        sb.append(DARMA)
                .append("Cmd:").append("StateChange").append(";")
                .append("Type:").append(message.getType()).append(";")
                .append("TimeStamp:").append(message.getTimeStamp()).append(";")
                .append("DeviceNo:").append(message.getDeviceNo()).append(";")
                .append("UserName:").append(message.getUserName())
                .append(END);

        return sb.toString();
    }

}
