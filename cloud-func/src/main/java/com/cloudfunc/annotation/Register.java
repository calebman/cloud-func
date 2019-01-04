package com.cloudfunc.annotation;

import java.lang.annotation.*;

/**
 * @author chenjianhui
 * @description 注册云函数
 * @date 2018/12/13
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Register {
    /**
     * 名称
     */
    String value() default "";

    /**
     * 描述
     */
    String desc() default "";
}
