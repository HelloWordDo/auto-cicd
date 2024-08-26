package com.arkmon.autocicd.annotations;

import java.lang.annotation.*;

/**
 * @author X.J
 * @date 2021/3/1
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessCheck {
}
