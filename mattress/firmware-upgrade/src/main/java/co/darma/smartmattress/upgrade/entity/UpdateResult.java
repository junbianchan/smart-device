package co.darma.smartmattress.upgrade.entity;

import co.darma.smartmattress.entity.Element;
import co.darma.smartmattress.util.ByteUtils;

import java.util.Arrays;

/**
 * Created by frank on 15/12/22.
 */
public class UpdateResult extends Element {

    private byte head;

    private int length;

    private int command;

    private String deviceNo;

    private int seq;

    private byte[] checkSum;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
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

    public byte[] getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(byte[] checkSum) {
        this.checkSum = checkSum;
    }


    @Override
    public String toString() {
        return "UpdateResult{" +
                "head=" + Integer.toHexString((head >> 4) & 0xF) + Integer.toHexString(head & 0xF) +
                ", length=" + length +
                ", command=" + ByteUtils.hexToString(ByteUtils.getSubByte(ByteUtils.int2byteArray(this.getCommand()), 2, 2)) +
                ", deviceNo='" + deviceNo + '\'' +
                ", seq=" + seq +
                ", checkSum=" + Arrays.toString(checkSum) +
                '}';
    }
}
