package co.darma.smartmattress.entity;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by frank on 15/10/12.
 */
public class PacketContext {

    private ChannelHandlerContext context;

    private MattressPacket packet;

    private byte[] srcByte;

    public PacketContext(MattressPacket packet) {
        this.setPacket(packet);
    }

    public MattressPacket getPacket() {
        return packet;
    }

    public void setPacket(MattressPacket packet) {
        this.packet = packet;
    }

    public byte[] getSrcByte() {
        return srcByte;
    }

    public void setSrcByte(byte[] srcByte) {
        this.srcByte = srcByte;
    }

    public ChannelHandlerContext getContext() {
        return context;
    }

    public void setContext(ChannelHandlerContext context) {
        this.context = context;
    }
}
