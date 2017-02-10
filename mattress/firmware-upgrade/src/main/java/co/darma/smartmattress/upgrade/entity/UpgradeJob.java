package co.darma.smartmattress.upgrade.entity;

import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.util.ByteUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 升级一个任务，定了该模型，用于进行升级的时候对
 * 升级进行控制。
 * <p/>
 * Created by frank on 15/12/18.
 */
public class UpgradeJob {

    private static String systemBasePath = null;

    private Device device;

    private List<UpgradePacket> upgradePackets = new ArrayList<UpgradePacket>();

    public static final byte UP_HEARD = (byte) 0xFE;

    /**
     * 下发命令提示要升级
     */
    public static final int UP_REQ_COMMAND = 0x1D;

    /**
     * 下发命令提示本次升级包的大小
     */
    public static final int UP_INFO_COMMAND = 0x1A;

    /**
     * 下发命令提示本次升级的内容
     */
    public static final int UP_CONTENT_COMMAND = 0x1B;

    /**
     * 下发命令提示本次升级结束
     */
    public static final byte UP_END_COMMAND = 0x1C;

    /**
     * 升级包每次最大大小
     */
    private static final int PACKET_BYTE_LIMIT = 180;

    public static final int UP_CHECKSUM = (int) 0xFFFF;

    private static Logger logger = Logger.getLogger(UpgradeJob.class);


    /**
     * HEAD 长度 length长度 cmd长度 seq长度 crc 长度
     */
    public static final int COMMON_LENGHT = (1 + 2 + 1 + 2 + 2);

    private void initUpgradePackets(Firmware firmware) throws IOException {
        String filePath = firmware.getFirmwarePath();
        String fileName;
        if (StringUtils.isNoneBlank(filePath) && filePath.startsWith("/")) {
            fileName = filePath;
        } else {
            fileName = systemBasePath + filePath;
        }

        File file = new File(fileName);
        if (!file.exists())
            throw new FileNotFoundException("File :" + fileName + " not found.");

        FileInputStream fis = new FileInputStream(file);

        try {
            byte[] packetByte = new byte[PACKET_BYTE_LIMIT];
            int size;
            int seq = 2;// 前两个留
            int totalFileSize = 0;
            while ((size = fis.read(packetByte)) > 0) {
                UpgradePacket packet = new UpgradePacket();
                packet.setDeviceNo(device.getDeviceNo());

                packet.setHead(UP_HEARD);
                packet.setLength(size + COMMON_LENGHT);
                packet.setCommand(UP_CONTENT_COMMAND);
                packet.setSeq(seq++);
                packet.setContent(packetByte);
                packet.setCheckSum(UP_CHECKSUM);
                upgradePackets.add(packet);
                packetByte = new byte[PACKET_BYTE_LIMIT];
                totalFileSize += size;
            }
            if (StringUtils.isEmpty(firmware.getCheckSum())) {
                throw new IllegalArgumentException("Checksum is null or invalid :" + firmware.getCheckSum());
            }
            byte[] checkSum = ByteUtils.hexStringToByteArray(firmware.getCheckSum());
            UpgradePacket reqPacket = buildUpradeRequestPacket(device.getDeviceNo(), 0);
            upgradePackets.add(0, reqPacket);
            UpgradePacket upgradeInfoPacket = buildUpgradeInfoPacket(device.getDeviceNo(), 1, totalFileSize, checkSum);
            upgradePackets.add(1, upgradeInfoPacket);
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * 构建提示固件长度信息的包
     *
     * @param deviceNo
     * @param seq
     * @param totalFileSize
     * @return
     */
    private UpgradePacket buildUpgradeInfoPacket(String deviceNo, int seq, int totalFileSize, byte[] checksum) {
        UpgradePacket packet = new UpgradePacket();
        packet.setDeviceNo(deviceNo);
        packet.setHead(UP_HEARD);
        packet.setCommand(UP_INFO_COMMAND);
        packet.setSeq(seq);

        byte[] fileLength = ByteUtils.getSubByte(ByteUtils.int2byteArray(totalFileSize), 2, 2);
        byte[] totalContent = new byte[fileLength.length + checksum.length];

        System.arraycopy(fileLength, 0, totalContent, 0, fileLength.length);
        System.arraycopy(checksum, 0, totalContent, fileLength.length, checksum.length);
        packet.setContent(totalContent);

        packet.setLength(4 + COMMON_LENGHT);
        packet.setCheckSum(UP_CHECKSUM);

        return packet;
    }

    /**
     * 构建提示要升级的包
     *
     * @param deviceNo
     * @param seq
     * @return
     */
    private UpgradePacket buildUpradeRequestPacket(String deviceNo, int seq) {
        UpgradePacket packet = new UpgradePacket();
        packet.setDeviceNo(deviceNo);
        packet.setHead(UP_HEARD);
        packet.setCommand(UP_REQ_COMMAND);
        packet.setSeq(seq);
        packet.setLength(COMMON_LENGHT);
        packet.setCheckSum(UP_CHECKSUM);
        return packet;
    }


    public UpgradeJob(UpgradeRequest request) throws IOException {
        device = request.getDevice();
        initUpgradePackets(request.getTargetFirmware());
    }


    private int currentPacketPos = 0;

    public boolean hasNext() {
        return (currentPacketPos < upgradePackets.size());

    }

    public UpgradePacket next() {
        return upgradePackets.get(currentPacketPos++);
    }

    public Device getDevice() {
        return device;
    }


    public static void setSystemBasePath(String systemBasePath) {
        logger.info("base path is :" + systemBasePath);
        UpgradeJob.systemBasePath = systemBasePath;
    }
}
