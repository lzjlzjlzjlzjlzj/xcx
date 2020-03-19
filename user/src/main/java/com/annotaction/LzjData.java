package com.annotaction;

import java.lang.annotation.*;

/**
 * @author lzj
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LzjData{
    public String value() default "";
}
