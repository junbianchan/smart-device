package co.darma.smartmattress.tool;

import co.darma.smartmattress.constant.ErrorCodeConstant;
import co.darma.smartmattress.entity.MattressPacket;
import co.darma.smartmattress.entity.PacketContext;
import co.darma.smartmattress.entity.ValidateResult;
import co.darma.smartmattress.util.CrcAlgorithm;

/**
 * 校验和校验
 * Created by frank on 15/10/12.
 */
public class CheckSumValidator implements Validate {


    public ValidateResult doValidate(PacketContext packetContext) {

        MattressPacket packet = packetContext.getPacket();

        byte[] srcData = packetContext.getSrcByte();

        int crcResult = CrcAlgorithm.dm_crc16(0, srcData, srcData.length - 2);

        int packetCrc = packet.getChecksum();

        //TODO 校验码计算
//        if (crcResult != packetCrc) {
//            ValidateResult result = new ValidateResult();
//              result.setErrorCode(ErrorCodeConstant.ERROR_MATTRESS_CLIENT_CRC_ERROR);
//              result.setErrorMessage("Crc Inconsistent. In packet is " + packet.getChecksum() + ", actual is " + packetCrc);
//        }

        return null;
    }
}
