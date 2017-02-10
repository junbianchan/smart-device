package co.darma.smartmattress.analysis.entity;

import co.darma.smartmattress.entity.Element;
import co.darma.smartmattress.entity.MattressPacket;

/**
 * 业务分析包
 * Created by frank on 15/10/12.
 */
public class ServicePacket {

    /**
     * 报文附带时间戳
     */
    private long timestamp;

    /**
     * 设备序列化ID
     */
    private String deviceNo;

    /**
     * 报文实际业务数据
     */
    private byte[] data;

    private int cmd;

    private int packetNo;

    private int[] intData;

    public ServicePacket() {
    }

    public ServicePacket(String devceNo) {
        this.deviceNo = devceNo;
    }

    public ServicePacket(Element e) {

        if (e instanceof MattressPacket) {
            MattressPacket packet = (MattressPacket) e;
            this.data = packet.getData();
            this.timestamp = packet.getTimestamp();
            this.deviceNo = packet.getDeviceId();
            this.intData = getDataIntArray();
            this.cmd = packet.getCommand();
            this.packetNo = packet.getSeqNum();
        }
    }

    /**
     * 讲byte转成int
     *
     * @return
     */
    private int[] getDataIntArray() {
        if (this.data != null) {
            int[] array = new int[this.data.length / 2];
            for (int i = 0; i < array.length; ++i) {
                array[i] = ((this.data[2 * i] & 0xFF) << 8) + ((this.data[2 * i + 1]) & 0xFF);
            }
            return array;
        }
        return null;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int[] getIntData() {
        return intData;
    }

    public void setIntData(int[] intData) {
        this.intData = intData;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }


    public int getPacketNo() {
        return packetNo;
    }

    public void setPacketNo(int packetNo) {
        this.packetNo = packetNo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[deviceNo:").append(this.deviceNo).append(",time:").append(this.getTimestamp());
//                .append(",data:");
//        if (ArrayUtils.isNotEmpty(this.getIntData())) {
//            for (int data : this.getIntData()) {
//                sb.append(data).append(",");
//            }
//        }
        sb.append("]");
        return sb.toString();
    }
}
