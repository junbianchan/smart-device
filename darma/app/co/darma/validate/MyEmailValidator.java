package co.darma.validate;

import co.darma.constant.ErrorCode;
import co.darma.constant.ErrorMessageEN;
import co.darma.models.returns.Argument;
import co.darma.models.returns.ValidateResult;

import org.apache.commons.validator.EmailValidator;

import java.util.List;


/**
 * Created by frank on 15/11/18.
 */
public class MyEmailValidator extends Validator {

    private String validateField;

    private static EmailValidator emailValidator = EmailValidator.getInstance();


    @Override
    public ValidateResult validate(Argument arg) {
        Object obj = arg.getArgumentByName(validateField);
        if (obj != null) {
            String emailAddress = (String) obj;
            if (emailValidator.isValid(emailAddress)) {
                return null;
            } else {
                ValidateResult result = new ValidateResult(ErrorCode.EMAIL_FORMAT_ERROR,
                        ErrorMessageEN.EMAIL_FORMAT_ERROR);
                return result;
            }
        }
        return null;
    }

    @Override
    public ValidateResult validate(List<Argument> argumentList) {
        return null;
    }

    public void setValidateField(String validateField) {
        this.validateField = validateField;
    }

    public String getValidateField() {
        return validateField;
    }

}
