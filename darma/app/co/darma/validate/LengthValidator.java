package co.darma.validate;

import co.darma.constant.ErrorCode;
import co.darma.constant.ErrorMessageEN;
import co.darma.models.returns.Argument;
import co.darma.models.returns.ValidateResult;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by frank on 15/11/21.
 */
public class LengthValidator extends Validator {

    /**
     * 校验字段
     */
    private List<ValidatorFactor> validateFields = new LinkedList<ValidatorFactor>();

    public void addFieldFactor(ValidatorFactor factor) {
        if (factor != null) {
            validateFields.add(factor);
        }
    }

    @Override
    public ValidateResult validate(Argument arg) {

        if (validateFields != null && validateFields.size() > 0) {

            for (ValidatorFactor factor : validateFields) {
                Object obj = arg.getArgumentByName(factor.getValidateField());
                if (obj != null) {
                    String fieldValue = (String) obj;
                    if (fieldValue.length() > factor.getMaxLength()) {
                        ValidateResult result = new ValidateResult(ErrorCode.FIELD_LENGH_TOO_LONG,
                                ErrorMessageEN.EMAIL_FORMAT_ERROR);
                        return result;
                    }
                } else {
                    if (factor.getMinLength() > 0) {
                        ValidateResult result = new ValidateResult(ErrorCode.FIELD_LENGTH_NOT_EMPTY,
                                ErrorMessageEN.EMAIL_FORMAT_ERROR);
                        return result;
                    }
                }
            }
        }
        return null;
    }


    public List<ValidatorFactor> getValidateFields() {
        return validateFields;
    }

    public void setValidateFields(List<ValidatorFactor> validateFields) {
        this.validateFields = validateFields;
    }
}
