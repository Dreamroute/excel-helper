/**
 * 
 */
package com.github.dreamroute.excel.helper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author w.dehai 2019年1月17日
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateColumn {
    
    /**
     * 原始日期格式
     */
    String originalDateFormate() default "timestamp";
    
    /***
     * 目标日期格式
     *
     * import fomat when meet {@link DateColumn}
     */
    String targetDateFormate() default "yyyy/MM/dd HH:mm:ss";

}
