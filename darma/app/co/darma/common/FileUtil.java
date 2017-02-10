package co.darma.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * Created by frank on 15/11/30.
 */
public class FileUtil {


    private static String getBasePath() {
        return System.getProperty("picBasePath", "/home/ubuntu/darma-1.0-SNAPSHOT/pic");
    }

    public static String saveFile(File file) throws FileNotFoundException {


        String uuid = UUID.randomUUID().toString();

        String savePath = getBasePath() + "/" + uuid + ".gif";

        FileOutputStream fops = new FileOutputStream(savePath);

        FileInputStream fips = new FileInputStream(file);


        return null;

    }


}
