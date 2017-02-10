package co.darma.validate;

/**
 * Created by frank on 15/11/21.
 */
public class ValidatorFactor {

    /**
     * 校验字段
     */
    private String validateField;

    /**
     * 最大字段
     */
    private int maxLength;

    /**
     * 最小长度
     */
    private int minLength = -1;

    public String getValidateField() {
        return validateField;
    }

    public void setValidateField(String validateField) {
        this.validateField = validateField;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

}