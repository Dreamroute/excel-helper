package com.github.dreamroute.excel.helper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

/**
 * sheet cell's properties
 * 
 * @author 342252328@qq.com
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cell {

    /**
     * Horizontal Aign
     * 
     * @return return horizontal align
     */
    HorizontalAlignment horizontal() default HorizontalAlignment.CENTER;

    /**
     * Vertical Align
     * 
     * @return return vertical align
     */
    VerticalAlignment vertical() default VerticalAlignment.CENTER;

    /***
     * 暂时未使用此属性
     *
     * import fomat when meet {@link Date}
     */
    String dateFomate() default "yyyy-MM-dd HH:mm:ss";

}
