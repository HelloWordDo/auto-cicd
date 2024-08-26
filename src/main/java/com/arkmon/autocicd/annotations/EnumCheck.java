package com.arkmon.autocicd.annotations;

import com.arkmon.autocicd.utils.EnumUtil;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author X.J
 * @date 2021/2/7
 */


@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumCheck.Validator.class)
public @interface EnumCheck {
    String message() default "{enum.value.invalid}"; // 错误信息
    Class<? extends Enum<?>> enumClass(); // 枚举类
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String enumMethodCode() default "getCode"; // 枚举校验方法
    String enumMethodName() default "getName"; // 枚举校验方法
    boolean allowNull() default false; // 是否允许为空


    class Validator implements ConstraintValidator<EnumCheck, Object> {
        private Class<? extends Enum<?>> enumClass;
        private String enumMethodCode;
        private String enumMethodName;
        private boolean allowNull;
        @Override
        public void initialize(EnumCheck enumValue) {
            enumMethodCode = enumValue.enumMethodCode();
            enumMethodName = enumValue.enumMethodName();
            enumClass = enumValue.enumClass();
            allowNull = enumValue.allowNull();
        }
        @Override
        public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
            if (value == null) {
                return allowNull;
            }
            if (enumClass == null) {
                return Boolean.TRUE;
            }
            Enum<?> enumByParameter = EnumUtil.checkEnumByParameter(enumClass, enumMethodCode, value);
            if (enumByParameter != null) {
                return true;
            }
            Enum<?> enumByParameter1 = EnumUtil.checkEnumByParameter(enumClass, enumMethodName, value.toString().toLowerCase());
            if (enumByParameter1 != null) {
                return true;
            }
            return false;
        }
    }
}
