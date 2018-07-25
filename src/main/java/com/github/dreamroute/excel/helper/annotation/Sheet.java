package com.github.dreamroute.excel.helper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * sheet properties
 * 
 * @author 342252328@qq.com
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Sheet {

    /**
     * sheet's name, must not be empty
     * 
     * @return return sheet's name
     */
    String name();

}
