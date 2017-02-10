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
public class PasswordValidator extends Validator {

    private String validateField;

    private static final String PASSWORD_REG = "(^\\d+$)|(^[A-Z]+$)|(^[a-z]+$)";

    private Pattern pattern = Pattern.compile(PASSWORD_REG);

    @Override
    public ValidateResult validate(Argument arg) {

        Object obj = arg.getArgumentByName(validateField);
        if (obj != null) {
            String password = (String) obj;

            if (StringUtils.isEmpty(password) || password.length() < 8) {
                ValidateResult result = new ValidateResult(ErrorCode.PASSWORD_TOO_SHORT,
                        ErrorMessageEN.PASSWORD_TOO_SHORT);
                return result;
            }
            Matcher matcher = pattern.matcher(password);
            if (matcher.matches()) {
                ValidateResult result = new ValidateResult(ErrorCode.PASSWORD_NOT_RIGHT,
                        ErrorMessageEN.PASSWORD_FORMAT_ERROR);
                return result;
            }
            return null;
        }
        return null;
    }

    public String getValidateField() {
        return validateField;
    }

    public void setValidateField(String validateField) {
        this.validateField = validateField;
    }
}
