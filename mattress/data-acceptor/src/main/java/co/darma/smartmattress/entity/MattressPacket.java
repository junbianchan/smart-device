package co.darma.smartmattress.entity;

import co.darma.smartmattress.exception.ParseException;
import co.darma.smartmattress.util.ByteUtils;
import co.darma.smartmattress.parser.util.PacketConstant;
import org.apache.log4j.Logger;

/**
 * Created by frank on 15/9/23.
 */
public final class MattressPacket extends Element {

    private static final Logger logger = Logger.getLogger(MattressPacket.class);

    public MattressPacket() {
        super();
    }

    public void parsePacket(byte[] msg) throws ParseException {

        try {
            this.magicNumber = msg[PacketConstant.MAGIC_INDEX];

            this.length = ByteUtils.translate2ByteArrayToInt(ByteUtils.getSubByte(msg, PacketConstant.PACKAGE_LENGTH_INDEX,
                    PacketConstant.PACKAGE_LENGTH_SIZE));

            this.command = ByteUtils.translate2ByteArrayToInt(
                    ByteUtils.getSubByte(msg, PacketConstant.PACKAGE_CMD_INDEX,
                            PacketConstant.PACKAGE_CMD_SIZE)
            );

            byte[] seqByte = ByteUtils.getSubByte(msg, PacketConstant.PACKAGE_PACKET_NUM_INDEX,
                    PacketConstant.PACKAGE_PACKAGE_NUM_SIZE);

            this.seqNum = ByteUtils.translate2ByteArrayToInt(seqByte);

            this.timestamp = ByteUtils.translate4ByteArrayToLong(ByteUtils.getSubByte(msg, PacketConstant.PACKAGE_TIMESTAMP_INDEX,
                    PacketConstant.PACKAGE_TIMESTAMP_SIZE));

            this.deviceId = ByteUtils.hexToString(ByteUtils.getSubByte(msg, PacketConstant.PACKAGE_DEVICEID_INDEX,
                    PacketConstant.PACKAGE_DEVICEID_SIZE));

            this.data = ByteUtils.getSubByte(msg, PacketConstant.PACKAGE_DATA_INDEX,
                    msg.length - PacketConstant.PACKGE_CHECKSUM_SIZE - PacketConstant.PACKAGE_DATA_INDEX);

            this.checksum = ByteUtils.translate2ByteArrayToInt(ByteUtils.getSubByte(msg, msg.length - PacketConstant.PACKGE_CHECKSUM_SIZE,
                    PacketConstant.PACKGE_CHECKSUM_SIZE));


        } catch (Exception e) {
            logger.error(e);
            throw new ParseException(e);
        }
        if (!validate(msg, checksum)) {
            throw new ParseException("Data error ,checksum is :" + this.checksum);
        }
    }

    private boolean validate(byte[] msg, int checksum) {
        if (checksum != 65535) {
            return false;
        }
        return true;
    }

    /**
     * 魔数
     */
    private byte magicNumber;

    /*
    * 报文长度
     */
    private int length;

    /**
     * 命令
     */
    private int command;

    /**
     * 报文序列号
     */
    private int seqNum;

    /**
     * 报文附带时间戳
     */
    private long timestamp;

    /**
     * 设备序列化ID
     */
    private String deviceId;

    /**
     * 报文实际业务数据
     */
    private byte[] data;

    /**
     * 报文校验和
     */
    private int checksum;


    public int getChecksum() {
        return checksum;
    }


    public byte getMagicNumber() {
        return magicNumber;
    }


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getCommand() {
        return command;
    }


    public int getSeqNum() {
        return seqNum;
    }


    public long getTimestamp() {
        return timestamp;
    }


    public String getDeviceId() {
        return deviceId;
    }


    public byte[] getData() {
        return data;
    }

    public String getVersion() {

        return null;
    }

    @Override
    public String toString() {

        if (toStringStr == null) {

            synchronized (this) {
                if (toStringStr == null) {
                    StringBuilder sb = new StringBuilder();

                    sb.append("[magic:")
                            .append(Integer.toHexString(this.getMagicNumber() & 0xff));

                    sb.append(",length:" + this.getLength());

                    sb.append(",cmd:" + this.getCommand());

                    sb.append(",seqNum:" + this.getSeqNum());

                    sb.append(",timeStamp" + Integer.toHexString(((int) this.getTimestamp()) & 0xffff));

                    sb.append(",deviceId:" + this.getDeviceId());


                    StringBuilder data = new StringBuilder();

                    byte[] lastByte = this.data;
                    for (byte b : lastByte) {
                        data.append(Integer.toHexString(b & 0xff));
                    }

                    sb.append(",data:" + data);

                    sb.append(",checkSum:" + Integer.toHexString(this.getChecksum() & 0xff));

                    sb.append("]");

                    toStringStr = sb.toString();

                }
            }
        }
        return toStringStr;

    }

    private String toStringStr;


    public void changeCmdTypeTo(int command) {
        if (command > 0) {
            this.command = command;
        }
    }


    public void setMagicNumber(byte magicNumber) {
        this.magicNumber = magicNumber;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public void setSeqNum(int seqNum) {
        this.seqNum = seqNum;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setChecksum(int checksum) {
        this.checksum = checksum;
    }
}
