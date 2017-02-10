package co.darma.smartmattress;

import co.darma.smartmattress.analysis.dao.SleepStateDao;
import co.darma.smartmattress.analysis.dao.impl.ManBehaviorDaoImpl;
import co.darma.smartmattress.analysis.dao.impl.MotionForSleepDaoImpl;
import co.darma.smartmattress.analysis.entity.BodyMotionForSleep;
import co.darma.smartmattress.analysis.entity.SleepState;
import co.darma.smartmattress.ccb.dao.AccessTokenDao;
import co.darma.smartmattress.ccb.entity.AccessContext;
import co.darma.smartmattress.dao.DeviceDao;
import co.darma.smartmattress.entity.Device;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 15/11/16.
 */
public class AccessTokenDaoTest extends BaseTestCase {


    public void testQueryAcitveAccessContext() {

        AccessTokenDao dao = getContext().getBean(AccessTokenDao.class);

        System.out.println(dao.queryActiveAccessContext("2869e813ef9e4c51829c9d126cf52d2b"));
    }

    public void testSaveAcessContext() {

        AccessTokenDao dao = getContext().getBean(AccessTokenDao.class);

        AccessContext context = new AccessContext();

        context.setAccessToken("ddebb25bd0bd4308a49b819a03d8105d");
        context.setUserName("jackson");
        context.setExpiresIn(12345L);
        context.setIsActive(true);
        context.setCreateTime(124821218L);
        context.setTokenType("default");

        dao.updateAccessToken(context);

    }

    public static void main(String[] args) {

        String s = (String) null;
        System.out.println(s);
    }


    public void testDao() {
        DeviceDao dao = getContext().getBean(DeviceDao.class);

        System.out.println(dao.queryDeviceByDeviceNo("3fa19303a5df"));
    }


    public void testSaveMotionForSleep() {
        MotionForSleepDaoImpl dao = getContext().getBean(MotionForSleepDaoImpl.class);

        BodyMotionForSleep bodyMotionForSleep = new BodyMotionForSleep();
        bodyMotionForSleep.setDeviceId(12);
        bodyMotionForSleep.setStartTime(1451996599839L);
        bodyMotionForSleep.setEndTime(System.currentTimeMillis());
        bodyMotionForSleep.setMetaData("1,1,2,2,2,2,2,2,,1,1,1,2,2,3,2,,3,2,3,3");
        bodyMotionForSleep.setDataNumber(21);
        bodyMotionForSleep.setAlgorithmVersion("1.0");
        dao.saveOrUpdateBodyMotionForSleep(bodyMotionForSleep);
    }


    public void testQueryMotionForSleep() {
        MotionForSleepDaoImpl dao = getContext().getBean(MotionForSleepDaoImpl.class);
        System.out.println(dao.queryBodyMotionForSleep(12, 1451996579034L, 1451996599839L));
    }


    public void testSleepStateDaoImpl() {

        SleepStateDao dao = getContext().getBean(SleepStateDao.class);

        List<SleepState> sleepStates = new LinkedList<SleepState>();

        Device d = new Device();
        d.setId(22);


        SleepState s1 = new SleepState();
        s1.setDevice(d);
        s1.setStartTime(1453193834L);
        s1.setEndTime(System.currentTimeMillis() / 1000);
        s1.setSleepState(2);
        s1.setUpdateTime(System.currentTimeMillis());
        s1.setAlgorithmVersion("1.0");


        SleepState s2 = new SleepState();
        s2.setDevice(d);
        s2.setStartTime(1453193834L);
        s2.setEndTime(System.currentTimeMillis() / 1000);
        s2.setSleepState(2);
        s2.setUpdateTime(System.currentTimeMillis());
        s2.setAlgorithmVersion("1.0");


        sleepStates.add(s1);
        sleepStates.add(s2);


        dao.saveOrUpdateSleepSatte(sleepStates);

    }


    public void testSleepStateQuery() {


        SleepStateDao dao = getContext().getBean(SleepStateDao.class);

        System.out.println(dao.querySleepState(22, 1452080682585L));

    }


    public void testManBehaviorDao() {
        ManBehaviorDaoImpl dao = getContext().getBean(ManBehaviorDaoImpl.class);

        System.out.println(dao.queryLastestManBehaviorByDevice(26, 1451981734L));
    }


}
