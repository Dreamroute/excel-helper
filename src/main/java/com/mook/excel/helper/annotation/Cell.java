package com.mook.excel.helper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.usermodel.CellType;

/**
 * 单元格属性，也就是列属性
 * 
 * @author 342252328@qq.com
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cell {

    /**
     * 列名，必填
     */
    String name();

    /**
     * 单元格列宽（默认：0，自适应宽度，大于0生效，小于等于0无效）
     */
    int width() default 0;

    /**
     * 单元格值的类型，参考：{@link CellType}
     */
    CellType cellType() default CellType.STRING;

}
