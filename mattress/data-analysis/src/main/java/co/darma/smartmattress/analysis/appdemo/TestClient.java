package co.darma.smartmattress.analysis.appdemo;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by frank on 15/11/5.
 */
public class TestClient {

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("127.0.0.1", 17001);

        socket.setKeepAlive(true);

        //由系统标准输入设备构造BufferedReader对象

        OutputStream os = socket.getOutputStream();

        PrintWriter write = new PrintWriter(os);

        write.write("hello world!\n");

        write.flush();
        write.close();
        socket.close(); //关闭Socket

    }

}
