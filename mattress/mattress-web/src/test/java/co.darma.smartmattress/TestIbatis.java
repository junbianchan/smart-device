package co.darma.smartmattress;

import co.darma.smartmattress.analysis.dao.BodyMotionDao;
import co.darma.smartmattress.dao.DeviceDao;
import co.darma.smartmattress.dao.DeviceManRelationDao;
import co.darma.smartmattress.analysis.dao.ManBehaviorDao;
import co.darma.smartmattress.analysis.entity.*;
import co.darma.smartmattress.dao.UserDao;
import co.darma.smartmattress.dao.impl.UserDaoImpl;
import co.darma.smartmattress.entity.*;
import co.darma.smartmattress.exception.ParseException;
import co.darma.smartmattress.service.PacketHandleService;
import co.darma.smartmattress.util.ByteUtils;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 15/10/26.
 */
public class TestIbatis extends TestCase {

    private static Logger logger = Logger.getLogger(TestIbatis.class);

    private static ClassPathXmlApplicationContext context;

    static {
        context = new ClassPathXmlApplicationContext(
                "spring/applicationContext.xml",
                "spring/applicationContext_acceptor.xml",
                "spring/applicationContext_ibatis.xml",
                "spring/applicationContext_analysis.xml",
                "spring/applicationContext_ccb.xml"
        );
    }


    public void testDeviceInsert() {
        DeviceDao deviceDao = (DeviceDao) context.getBean("deviceDao");

        co.darma.smartmattress.entity.Device device = new co.darma.smartmattress.entity.Device();
        device.setDeviceNo("riwerk9");
        deviceDao.insertDevice(device);

        System.out.println("device id:" + device.getId());
    }

    public void testUserInfoQuery() {
        UserDao userDao = context.getBean(UserDaoImpl.class);
        UserInfo user = new UserInfo();
        user.setUserName("michael");
        user.setEmail("test@darma.co");
        user.setOnLine(true);
        user.setWeightKilo(1.211111111);
        user.setWeightPound(3.4212121212);
        user.setSex("F");
//        userDao.insertUserInfo(user);
        System.out.println(userDao.queryUserByUserName(user.getUserName()));
    }

    public void testQueryDeviceRelation() {
        DeviceManRelationDao dao = context.getBean(DeviceManRelationDao.class);
        System.out.println(dao.queryHeartByMan(2));
    }


    public void testSaveDeviceRelation() {
        DeviceManRelationDao dao = context.getBean(DeviceManRelationDao.class);

        List<DeviceManRelation> relations = new LinkedList<DeviceManRelation>();

        DeviceManRelation relation = new DeviceManRelation();

        relation.setDeviceId(2);
        relation.setUserId(211);
        relation.setBoundTime(12345L);
        relation.setIsActive(true);
        relation.setId(2);
        relations.add(relation);


        DeviceManRelation relation1 = new DeviceManRelation();

        relation1.setDeviceId(3);
        relation1.setUserId(211);
        relation1.setBoundTime(12345L);
        relation1.setIsActive(true);
        relation1.setUnboudTime(123132L);
        relation1.setId(1);
        relations.add(relation1);

        dao.updateDeviceManRelathionBatch(relations);


        System.out.println(dao.queryHeartByMan(2));

    }


    public void testInsertDevcie() {
        DeviceDao deviceDao = context.getBean(DeviceDao.class);
        co.darma.smartmattress.entity.Device device = new co.darma.smartmattress.entity.Device();
        device.setDeviceNo("123456");
        deviceDao.insertDevice(device);
    }

    public void testPacketHandleService() throws ParseException {

        PacketHandleService service = context.getBean(PacketHandleService.class);

        byte[] onesecond = getOneSecondeByte(0, (byte) 0x1);

        MattressPacket packet = new MattressPacket();
        packet.parsePacket(onesecond);
        PacketContext context = new PacketContext(packet);
        context.setSrcByte(onesecond);

        service.handle(context);
    }

    public void testSendOnPacket() throws IOException {
        byte[] onesecond = getOneSecondeByte(0, (byte) 0x5);

        Socket socket = new Socket("192.168.31.186", 17000);

        socket.setKeepAlive(true);


        //由系统标准输入设备构造BufferedReader对象

        OutputStream os = socket.getOutputStream();

        InputStream inputStream = socket.getInputStream();

        os.write(onesecond);
        os.flush();

        byte[] result = new byte[220];
        inputStream.read(result);
        System.out.println(ByteUtils.hexToString(result));
    }

    public void testPush() throws IOException {
        byte[] onesecond = getOneSecondeByte(0, (byte) 0x3);

        Socket socket = new Socket("114.215.181.106", 17000);

        socket.setKeepAlive(true);


        //由系统标准输入设备构造BufferedReader对象

        OutputStream os = socket.getOutputStream();

        InputStream inputStream = socket.getInputStream();

        os.write(onesecond);
        os.flush();

        byte[] result = new byte[10];
        inputStream.read(result);
        System.out.println(ByteUtils.hexToString(result));
    }


    public static byte[] getOneSecondeByte(int secondOffset, byte cmd) {

        int[] intData = new int[]{
                40347, 40344, 40318, 40331, 40328, 40332, 40329, 40319,
                40299, 40270, 40236, 40206, 40204, 40191, 40188, 40184,
                40171, 40154, 40158, 40181, 40184, 40184, 40173, 40162,
                40134, 40091, 40049, 40025, 40024, 40025, 40059, 40079,
                40084, 40070, 40053, 40054, 40087, 40137, 40164, 40217,
                40233, 40254, 40268, 40286, 40304, 40314, 40308, 40293,
                40287, 40287
        };

        byte[] onesecond = new byte[intData.length * 2];
        int i = 0;
        for (int data : intData) {
            onesecond[i] = (byte) ((data >> 8) & 0xFF);
            onesecond[i + 1] = (byte) (data & 0xFF);
            i += 2;
        }

        byte[] commondata = buildCommonHead(secondOffset, cmd, 0);

        byte[] finaldata = new byte[commondata.length + onesecond.length + 2];

        System.arraycopy(
                commondata, 0, finaldata, 0, commondata.length
        );

        System.arraycopy(
                onesecond, 0, finaldata, commondata.length, onesecond.length
        );

        finaldata[commondata.length + onesecond.length] = (byte) 0xFF;
        finaldata[commondata.length + onesecond.length + 1] = (byte) 0xFF;
        return finaldata;
    }

    public static void main(String[] args) {
        System.out.println(ByteUtils.hexToString(getUpgrade(0, (byte) 0x05, 0)));
    }

    public static void testUpgrade() throws IOException, InterruptedException {

//        byte[] errorSecondPacket = getError(0, (byte) 0x05, 1);

        byte[] onesecond = getUpgrade(0, (byte) 0x05, 1);

//        byte[] onesecond1 = getUpgrade(0, (byte) 0x05, 1);

//        Socket socket = new Socket("192.168.31.186", 17000);
        Socket socket = new Socket("mattress.darma.cn", 17000);

        socket.setKeepAlive(true);

        //由系统标准输入设备构造BufferedReader对象

        OutputStream os = socket.getOutputStream();

        InputStream inputStream = socket.getInputStream();

//        os.write(errorSecondPacket);
        os.flush();
//        Thread.sleep(50);
//        System.out.println("Error:" + ByteUtils.hexToString(errorSecondPacket));
        os.write(onesecond);
//        os.write(onesecond1);
        System.out.println("Right:" + ByteUtils.hexToString(onesecond));
//        System.out.println("onesecond1:" + ByteUtils.hexToString(onesecond1));
//        os.flush();

        byte[] result = new byte[220];

        while (inputStream.read(result) > 0) {
            byte cmd = ByteUtils.getSubByte(result, 3, 1)[0];
            if (cmd == 0x0a) {
                result = new byte[220];
                continue;
            }
            System.out.println("result is :" + ByteUtils.hexToString(result));
            byte[] data = getUpgrade(0, cmd, ByteUtils.translate2ByteArrayToInt(ByteUtils.getSubByte(result, 4, 2)));

            System.out.println("data is :" + ByteUtils.hexToString(data));

            os.write(data);
            result = new byte[220];
        }


    }

    private static byte[] getError(int secondOffset, byte cmd, int seq) {
        int[] intData = new int[]{5, 0};

        byte[] onesecond = new byte[2];
        onesecond[0] = 0x5;
        onesecond[1] = 0x0;

        byte[] commondata = buildCommonHeadError(secondOffset, cmd, seq);

        byte[] finaldata = new byte[commondata.length + onesecond.length + 2];

        System.arraycopy(
                commondata, 0, finaldata, 0, commondata.length
        );

        System.arraycopy(
                onesecond, 0, finaldata, commondata.length, onesecond.length
        );

        finaldata[commondata.length + onesecond.length] = (byte) 0xFF;
        finaldata[commondata.length + onesecond.length + 1] = (byte) 0xFF;
        return finaldata;

    }

    private static byte[] buildCommonHeadError(int secondOffset, byte cmd, int seq) {
        int curTime = 1445865206 + secondOffset;

        byte t1 = (byte) ((curTime >> 24) & 0xFF);
        byte t2 = (byte) ((curTime >> 16) & 0xFF);
        byte t3 = (byte) ((curTime >> 8) & 0xFF);
        byte t4 = (byte) ((curTime) & 0xFF);

        byte seq1 = (byte) ByteUtils.int2byteArray(seq)[2];
        byte seq2 = (byte) ByteUtils.int2byteArray(seq)[3];
        byte[] commonData = new byte[]{
                (byte) 0xFF,
                (byte) 0xFE,
                0x00, 0x14,
                cmd

        };
        return commonData;
    }


    public static byte[] getUpgrade(int secondOffset, byte cmd, int seq) {
        int[] intData = new int[]{5, 0};

        byte[] onesecond = new byte[2];
        onesecond[0] = 0x5;
        onesecond[1] = 0x0;

        byte[] commondata = buildCommonHead(secondOffset, cmd, seq);

        byte[] finaldata = new byte[commondata.length + onesecond.length + 2];

        System.arraycopy(
                commondata, 0, finaldata, 0, commondata.length
        );

        System.arraycopy(
                onesecond, 0, finaldata, commondata.length, onesecond.length
        );

        finaldata[commondata.length + onesecond.length] = (byte) 0xFF;
        finaldata[commondata.length + onesecond.length + 1] = (byte) 0xFF;
        return finaldata;
    }


    public static byte[] buildCommonHead(int second, byte cmd, int seq) {

        int curTime = 1445865206 + second;

        byte t1 = (byte) ((curTime >> 24) & 0xFF);
        byte t2 = (byte) ((curTime >> 16) & 0xFF);
        byte t3 = (byte) ((curTime >> 8) & 0xFF);
        byte t4 = (byte) ((curTime) & 0xFF);

        byte seq1 = (byte) ByteUtils.int2byteArray(seq)[2];
        byte seq2 = (byte) ByteUtils.int2byteArray(seq)[3];
        byte[] commonData = new byte[]{
                (byte) 0xFE,
                0x00, 0x14,
                cmd,
                seq1, seq2,
                t1, t2, t3, t4,
                0x68,0x80,0xf7,0x2f,0x60,0xca,
        };
        return commonData;
    }


    public void testSaveBodyMotion() {
        BodyMotionDao bodyMotionDao = context.getBean(BodyMotionDao.class);
        List<BodyMotion> bodyMotionList = new LinkedList<BodyMotion>();
        BodyMotion b = new BodyMotion();
        b.setLevel(BodyMotionType.getBodyMotionTypeByValue(3));
        b.setMarkTime(123456789L);
        b.setUserId(88);
        b.setMarkTime(12345678L);
        co.darma.smartmattress.entity.Device d = new co.darma.smartmattress.entity.Device();
        d.setId(124);
        b.setDevice(d);
        bodyMotionList.add(b);
        bodyMotionDao.saveOrUpdateBodyMotionList(bodyMotionList);
    }


    public void testSaveBehavior() {

        ManBehaviorDao dao = (ManBehaviorDao) context.getBean("manBehaviorDao");
        ManBehavior behavior = new ManBehavior();
        behavior.setUserId(123);
        behavior.setDeviceId(123);
        behavior.setType(ManBehaviorType.getTypeById(2));
        dao.saveManBehavior(behavior);

    }


}
