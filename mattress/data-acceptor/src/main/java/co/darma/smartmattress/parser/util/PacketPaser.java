package co.darma.smartmattress.parser.util;

import co.darma.smartmattress.entity.MattressPacket;
import co.darma.smartmattress.exception.ParseException;
import org.apache.log4j.Logger;

/**
 * Created by frank on 15/9/23.
 */
public class PacketPaser {

    private static Logger logger = Logger.getLogger(PacketPaser.class);

    public final static MattressPacket parserToGetPacket(byte[] msg) {
        MattressPacket packet = new MattressPacket();
        try {
            packet.parsePacket(msg);
            return packet;
        } catch (ParseException e) {
            logger.error(e);
            return null;
        }
    }


}
