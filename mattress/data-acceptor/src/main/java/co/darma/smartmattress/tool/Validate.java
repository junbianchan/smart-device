package co.darma.smartmattress.tool;

import co.darma.smartmattress.entity.PacketContext;
import co.darma.smartmattress.entity.ValidateResult;

/**
 * 校验类接口
 * Created by frank on 15/10/12.
 */
public interface Validate {

    public ValidateResult doValidate(PacketContext packetContext);
}
