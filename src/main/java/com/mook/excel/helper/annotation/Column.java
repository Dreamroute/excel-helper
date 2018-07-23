package com.mook.excel.helper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * the column's properties
 * 
 * @author 342252328@qq.com
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    /**
     * column name, must not bu empty
     */
    String name();

    /**
     * the default width of header/cell.(default: 0, means auto column width, less than 0 will be ignore, recomend 0.)
     */
    int width() default 0;

}
