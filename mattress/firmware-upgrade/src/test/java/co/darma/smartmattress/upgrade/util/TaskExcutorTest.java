package co.darma.smartmattress.upgrade.util;

import co.darma.smartmattress.QueueUtil;
import co.darma.smartmattress.entity.Element;
import co.darma.smartmattress.exception.SystemException;
import co.darma.smartmattress.upgrade.BaseTest;
import co.darma.smartmattress.upgrade.entity.UpdateResult;
import co.darma.smartmattress.upgrade.entity.UpgradeJob;
import co.darma.smartmattress.upgrade.entity.UpgradePacket;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by frank on 15/12/31.
 */
public class TaskExcutorTest extends BaseTest {

    @Test
    public void testStartFixTimeTask() throws Exception {

    }

    @Test
    public void testAddTask() throws Exception {
        UpgradeJob.setSystemBasePath("/Users/frank/darma/project/darmabackend/mattress/firmware-upgrade/");
        TaskExcutor.addTask();


        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    UpgradePacket element = null;
                    while ((element = (UpgradePacket) QueueUtil.takeStatePacket()) != null) {
                        int seq = element.getSeq();
                        UpdateResult updateResult = new UpdateResult();
                        updateResult.setDeviceNo(element.getDeviceNo());
                        updateResult.setSeq(seq);
                        QueueUtil.putUpgradeResultPacket(updateResult);
                    }

                } catch (SystemException e) {
                    e.printStackTrace();
                }


            }
        }).start();


        Thread.sleep(1000 * 1000);
    }

    @Test
    public void testIfThereChecker() throws Exception {

    }

    @Test
    public void testCleanUnRunningChecker() throws Exception {

    }

    @Test
    public void testGetCheckerList() throws Exception {

    }
}