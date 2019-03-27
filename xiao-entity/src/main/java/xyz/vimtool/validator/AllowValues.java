package xyz.vimtool.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 允许传入的参数校验注解
 *
 * @author  zhangzheng
 * @version 1.0.0
 * @date    2018/7/13
 */
@Constraint(validatedBy = AllowValuesValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AllowValues {

    String message() default "超出取值范围";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] allowValues() default {};
}
