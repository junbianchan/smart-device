package co.darma.smartmattress.analysis.spi;

import co.darma.smartmattress.analysis.entity.PushContextEntity;

/**
 * Created by frank on 15/11/18.
 */
public interface PushToClient {

    public boolean pushStatePacket(PushContextEntity message);
}
