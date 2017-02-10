package co.darma.validate;

import co.darma.models.returns.Argument;
import co.darma.models.returns.ValidateResult;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Created by frank on 15/11/18.
 */
public abstract class Validator {

    public abstract ValidateResult validate(Argument arg);

    public ValidateResult validate(List<Argument> argumentList) {
        if (CollectionUtils.isNotEmpty(argumentList)) {
            for (Argument argument : argumentList) {
                ValidateResult result = validate(argument);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;

    }
}