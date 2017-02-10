package co.darma.smartmattress.upgrade.entity;

import co.darma.smartmattress.entity.Element;
import co.darma.smartmattress.util.ByteUtils;

/**
 * 下发给设备的数据包
 * Created by frank on 15/12/17.
 */
public class UpgradePacket extends Element {

    private byte head;

    /**
     * content的实际有效长度
     */
    private int length;

    private int command;

    private String deviceNo;

    private int seq;

    private byte[] content;

    private int checkSum;

    public UpgradePacket() {
    }

    public byte getHead() {
        return head;
    }

    public void setHead(byte head) {
        this.head = head;
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

    public void setCommand(int command) {
        this.command = command;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(int checkSum) {
        this.checkSum = checkSum;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    private String toStringStr;

    @Override
    public String toString() {

        if (toStringStr == null) {

            synchronized (this) {
                if (toStringStr == null) {
                    StringBuilder sb = new StringBuilder();

                    sb.append("[magic:")
                            .append(Integer.toHexString(head & 0xff));

                    sb.append(",length:" + this.getLength());

                    sb.append(",cmd:" + ByteUtils.hexToString(ByteUtils.getSubByte(ByteUtils.int2byteArray(this.getCommand()), 2, 2)));

                    sb.append(",seqNum:" + this.seq);

                    sb.append(",deviceId:" + this.deviceNo);

                    sb.append(",checkSum:" + Integer.toHexString(this.getCheckSum() & 0xff));

                    sb.append("]");

                    toStringStr = sb.toString();

                }
            }
        }
        return toStringStr;

    }


}
