package com.mook.excel.helper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.usermodel.CellType;

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

    /**
     * cell type, you can define it with {@link CellType} by your self, support {@link CellType.STRING}, {@link CellType.NUMERIC} and {@link CellType.BOOLEAN}}, default: {@link CellType.STRING}
     */
    CellType cellType() default CellType.STRING;
    
    /**
     * excle sheet's column order.
     */
    int order() default 0;

}
