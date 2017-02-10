package co.darma.validate;

import co.darma.models.returns.Argument;
import co.darma.models.returns.ValidateResult;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by frank on 15/11/18.
 */
public class ValidatorHandler {

    public static Map<String, ValidatorHandler> handlerMap = new HashMap<String, ValidatorHandler>();

    public static final String PROFILE = "profile";

    public static final String SIGUP = "sigup";


    public static final String PHYSICAL_RECORD = "physicalRecord";


    public static final String COMPANY_SIGN_UP = "companySignUp";

    static {

        //校验器
        MyEmailValidator mev = new MyEmailValidator();
        mev.setValidateField("email");

        PasswordValidator pv = new PasswordValidator();
        pv.setValidateField("password");

        RequiredValidator requiredValidator = new RequiredValidator();
        requiredValidator.addFeild("firstName");
//        requiredValidator.addFeild("lastName");

        RegValidator genderValidator = new RegValidator();
        genderValidator.setValidateField("gender");
        genderValidator.setRegex("F|M");
        genderValidator.setErrorMessage("gender should be F or M.");

        WeightValidator weightValidator = new WeightValidator();
        weightValidator.addField("weightkg");
        weightValidator.addField("weightlbs");

        HeightValidator heightValidator = new HeightValidator();
        heightValidator.addField("heightcm");
        heightValidator.addField("heightinch");

        //长度校验
        ValidatorFactor emailLengthValidator = new ValidatorFactor();
        emailLengthValidator.setValidateField("email");
        emailLengthValidator.setMinLength(1);
        emailLengthValidator.setMaxLength(400);

        ValidatorFactor passwordLengthValidator = new ValidatorFactor();
        passwordLengthValidator.setValidateField("password");
        passwordLengthValidator.setMaxLength(1024);
        passwordLengthValidator.setMinLength(-1);

        ValidatorFactor firstNameLengthValidator = new ValidatorFactor();
        firstNameLengthValidator.setValidateField("firstName");
        firstNameLengthValidator.setMaxLength(256);
        firstNameLengthValidator.setMinLength(-1);

        ValidatorFactor lastNameLengthValidator = new ValidatorFactor();
        lastNameLengthValidator.setValidateField("firstName");
        lastNameLengthValidator.setMaxLength(256);
        lastNameLengthValidator.setMinLength(-1);

        LengthValidator signUpLengthValidator = new LengthValidator();
        signUpLengthValidator.addFieldFactor(emailLengthValidator);
        signUpLengthValidator.addFieldFactor(passwordLengthValidator);

        LengthValidator profileLengthValidator = new LengthValidator();
        profileLengthValidator.addFieldFactor(firstNameLengthValidator);
        profileLengthValidator.addFieldFactor(lastNameLengthValidator);


        //校验器列表
        List<Validator> sigupValidators = new LinkedList<Validator>();
        List<Validator> profileValidators = new LinkedList<Validator>();

        sigupValidators.add(signUpLengthValidator);
        sigupValidators.add(mev);
        sigupValidators.add(pv);

        profileValidators.add(requiredValidator);
        profileValidators.add(profileLengthValidator);
        profileValidators.add(genderValidator);
        profileValidators.add(weightValidator);
        profileValidators.add(heightValidator);

        //校验器列表容器
        ValidatorHandler siginUpHandler = new ValidatorHandler();
        ValidatorHandler profieHandle = new ValidatorHandler();


        siginUpHandler.setValidators(sigupValidators);
        profieHandle.setValidators(profileValidators);


        handlerMap.put(SIGUP, siginUpHandler);
        handlerMap.put(PROFILE, profieHandle);

    }

    {


    }

    public static ValidatorHandler getValidatorHandlerByName(String name) {
        return handlerMap.get(name);
    }

    private List<Validator> validators;

    public ValidateResult validate(Argument arg) {

        if (!CollectionUtils.isEmpty(validators)) {

            for (Validator v : validators) {
                ValidateResult result = v.validate(arg);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    /**
     * 校验参数列表
     *
     * @param list
     * @return
     */
    public ValidateResult validateList(List<Argument> list) {

        if (!CollectionUtils.isEmpty(list)) {

            for (Validator v : validators) {
                ValidateResult result = v.validate(list);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }
}
