package com.github.dreamroute.excel.helper.annotation;

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
     * 
     * @return return column name
     */
    String name();

    /**
     * the default width of header/cell.(default: 0, means auto column width, less than 0 will be ignore, recomend 0.)
     * 
     * @return column width
     */
    int width() default 0;

    /**
     * cell type, you can define it with <code>CellType</code> by yourself, support <code>CellType.STRING</code>, <code>CellType.NUMERIC</code> and <code>CellType.BOOLEAN</code>, default:
     * <code>CellType.STRING</code>
     * 
     * @return return cellType.
     */
    CellType cellType() default CellType.STRING;

    /**
     * excle sheet's column order.
     * 
     * @return return column order, in future
     */
    int order() default 0;

}
