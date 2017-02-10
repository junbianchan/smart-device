package co.darma.validate;

import co.darma.constant.ErrorCode;
import co.darma.constant.ErrorMessageEN;
import co.darma.models.returns.Argument;
import co.darma.models.returns.ValidateResult;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by frank on 15/11/18.
 */
public class RegValidator extends Validator {

    /**
     * 校验字段
     */
    private String validateField;

    /**
     * 校验正则
     */
    private String regex;

    private Pattern pattern;

    private int errorCode;

    private String errorMessage;

    @Override
    public ValidateResult validate(Argument arg) {

        Object obj = arg.getArgumentByName(validateField);
        if (obj != null) {

            String target = String.valueOf(obj);
            Matcher matcher = pattern.matcher(target);
            if (matcher.matches()) {
                return null;
            } else {
                ValidateResult result = new ValidateResult(ErrorCode.INVALID_ARGUMENT,
                        getErrorMessage());
                return result;
            }


        }
        return null;
    }

    public String getValidateField() {
        return validateField;
    }

    public void setValidateField(String validateField) {
        this.validateField = validateField;
    }


    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {

        this.regex = regex;
        if (StringUtils.isNotBlank(regex)) {
            pattern = Pattern.compile(regex);
        }
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
