package com.nhdasystem.util;


import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface RegexPattern{

    public String regexp() default "";
    public String message() default "";

}
