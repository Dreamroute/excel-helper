package com.mook.excel.helper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.usermodel.CellType;

/**
 * cell's properties, means the column's properties
 * 
 * @author 342252328@qq.com
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cell {

    /**
     * column name, must not bu empty
     */
    String name();

    /**
     * the default width of cell.(default: 0, means auto column width, less than 0.
     * will be ignore.)
     */
    int width() default 0;

    /**
     * the type of cell, see: {@link CellType}
     */
    CellType cellType() default CellType.STRING;

}
