package co.darma.smartmattress.constant;

/**
 * Created by frank on 16/1/11.
 */
public class PacketConstant {

    public static byte HEARD = (byte) 0xFE;

    /**
     * server发给床垫的命令,表示已经收到离床包
     */
    public static byte OFFLINE_BACK_CMD = 0xB;

    /**
     * server发给床垫的命令,表示要床垫同步时间
     */
    public static final byte SYNC_TIME_BACK_CMD = 0xA;

    /**
     * 床垫给sever发送命令，表示同步时间完成
     */
    public static final int SYNC_TIME_ACK_CMD = 6;

    /**
     * 床垫发送同步时间的包的命令
     */
    public static final int SYNC_TIME_CMD = 5;

    /**
     * 当我检测到当前系统发送过的包时间过小，会将包的命令改成这个命令
     */
    public static final int SYNC_TIME_SHADOW_CMD = 9;

    /**
     * 表示离线了
     */
    public static byte OFFLINE_CMD = 0x08;

    public static final int AWAY_BED = 3;

    public static final int UP_BED = 4;

    /**
     * 表示离线了
     */
    public static final int OFFLINE = 8;


}
