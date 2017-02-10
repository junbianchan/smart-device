package co.darma.smartmattress.tool;

import co.darma.smartmattress.entity.PacketContext;
import co.darma.smartmattress.entity.ValidateResult;

import java.util.LinkedList;
import java.util.List;

/**
 * 校验器的Handle
 * Created by frank on 15/10/12.
 */
public class ValidatorHandler {


    private List<Validate> validateList;

    public ValidateResult doHandle(PacketContext context) {

        ValidateResult result;
        for (Validate validate : validateList) {
            result = validate.doValidate(context);

            if (result != null) {
                return result;
            }

        }
        return null;
    }

    public void setValidateList(List<Validate> validateList) {
        this.validateList = validateList;
    }


}
