package com.github.dreamroute.excel.helper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * sheet header's properties
 * 
 * @author 342252328@qq.com
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Header {

    /** Horizontal Aign **/
    HorizontalAlignment horizontal() default HorizontalAlignment.CENTER;
    
    /** Vertical Align **/
    VerticalAlignment vertical() default VerticalAlignment.CENTER;
    
    
    
}
