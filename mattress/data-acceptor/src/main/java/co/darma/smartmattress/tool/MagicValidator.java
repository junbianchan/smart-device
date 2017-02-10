package co.darma.smartmattress.tool;

import co.darma.smartmattress.constant.ErrorCodeConstant;
import co.darma.smartmattress.entity.MattressPacket;
import co.darma.smartmattress.entity.PacketContext;
import co.darma.smartmattress.entity.ValidateResult;

/**
 * Created by frank on 15/10/19.
 */
public class MagicValidator implements Validate {

    private static final byte DEFAULT_MAGIC = (byte) 0XFE;

    public ValidateResult doValidate(PacketContext packetContext) {

        ValidateResult result = null;
        MattressPacket packet = null;
        if (packetContext != null && (packet = packetContext.getPacket()) != null) {

            if (packet.getMagicNumber() != DEFAULT_MAGIC) {
                result = new ValidateResult();
                result.setErrorCode(ErrorCodeConstant.ERROR_CODE_MAGIC);
                result.setErrorMessage("Magic number ["
                        + Integer.toHexString(packet.getMagicNumber() & 0xFF)
                        + "] is invalid, Which is OxFE.");
            }
        }

        return result;
    }
}
