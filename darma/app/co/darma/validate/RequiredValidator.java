package co.darma.validate;

import co.darma.constant.ErrorCode;
import co.darma.constant.ErrorMessageEN;
import co.darma.models.returns.Argument;
import co.darma.models.returns.SignInResult;
import co.darma.models.returns.ValidateResult;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by frank on 15/11/19.
 */
public class RequiredValidator extends Validator {

    private List<String> validateFieldList;

    @Override
    public ValidateResult validate(Argument arg) {
        if (validateFieldList != null && validateFieldList.size() > 0) {

            for (String validateField : validateFieldList) {
                Object obj = arg.getArgumentByName(validateField);
                if (obj == null || StringUtils.isBlank(obj.toString())) {
                    ValidateResult result = new ValidateResult(ErrorCode.PARAMTER_REQUIRED,
                            validateField + ErrorMessageEN.REQUIRED);
                    return result;
                }
            }
        }
        return null;
    }

    public void addFeild(String fieldName) {
        if (validateFieldList == null) {
            validateFieldList = new LinkedList<String>();
        }
        validateFieldList.add(fieldName);
    }

    public List<String> getValidateFieldList() {
        return validateFieldList;
    }

    public void setValidateFieldList(List<String> validateFieldList) {
        this.validateFieldList = validateFieldList;
    }

}
