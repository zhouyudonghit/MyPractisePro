package com.example.localuser.retrofittest.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ASM功能注解，用于标记需要自动注册的功能入口
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AsmFunction {
    /**
     * 功能名称
     */
    String functionName();

    /**
     * 是否可见，默认为true
     */
    boolean visible() default true;
}
