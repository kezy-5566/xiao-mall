package xyz.vimtool.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 参数取值注解验证器
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/7/13
 */
public class AllowValuesValidator implements ConstraintValidator<AllowValues, String> {

    private String[] allowValues;

    @Override
    public void initialize(AllowValues constraintAnnotation) {
        this.allowValues = constraintAnnotation.allowValues();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.trim().length() == 0) {
            return false;
        }

        if (allowValues.length == 0) {
            return false;
        }

        for (String allowValue : allowValues) {
            if (allowValue.equals(value)) {
                return true;
            }
        }

        return false;
    }
}
