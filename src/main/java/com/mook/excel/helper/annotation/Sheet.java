package com.mook.excel.helper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * sheet相关属性
 * 
 * @author 342252328@qq.com
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Sheet {

    /**
     * sheet名称，必填
     * 
     */
    String name();

}
