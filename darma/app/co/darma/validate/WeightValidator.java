package co.darma.validate;

import co.darma.constant.ErrorCode;
import co.darma.constant.ErrorMessageEN;
import co.darma.models.returns.Argument;
import co.darma.models.returns.ValidateResult;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * 体重校验
 * Created by frank on 15/11/20.
 */
public class WeightValidator extends Validator {

    private List<String> weightFieldList = new LinkedList<String>();

    @Override
    public ValidateResult validate(Argument arg) {

        Integer weight = -1;

        if (weightFieldList != null && weightFieldList.size() > 0) {
            for (String fieldName : weightFieldList) {
                Object obj = arg.getArgumentByName(fieldName);
                if (obj != null) {
                    Integer weightObj = (Integer) obj;
                    if (weightObj > 0) {
                        if (StringUtils.equals(fieldName, "heightcm")) {
                            if (weightObj < 60 || weightObj > 300) {
                                ValidateResult result = new ValidateResult(ErrorCode.HEIGHT_REQUIRED,
                                        ErrorMessageEN.HEIGHT_ERROR);
                                return result;
                            }
                        } else if (StringUtils.equals(fieldName, "heightinch")) {
                            if (weightObj < 12 || weightObj > 108) {
                                ValidateResult result = new ValidateResult(ErrorCode.HEIGHT_REQUIRED,
                                        ErrorMessageEN.HEIGHT_ERROR);
                                return result;
                            }
                        }
                        weight = weightObj;
                    }
                }
            }
        }

        if (weight <= 0) {
            ValidateResult result = new ValidateResult(ErrorCode.WEIGHT_REQUIRED,
                    ErrorMessageEN.WEIGHT_ERROR);
            return result;
        }

        return null;
    }

    public void addField(String fieldName) {
        weightFieldList.add(fieldName);
    }


}
