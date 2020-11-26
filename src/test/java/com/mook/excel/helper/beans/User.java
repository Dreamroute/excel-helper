package com.mook.excel.helper.beans;

import com.github.dreamroute.excel.helper.annotation.Column;
import com.github.dreamroute.excel.helper.annotation.DateColumn;
import com.github.dreamroute.excel.helper.annotation.Sheet;
import lombok.Data;
import org.apache.poi.ss.usermodel.CellType;

import java.io.Serializable;

/**
 * 
 * @author 342252328@qq.com
 *
 */
@Data
@Sheet(name = "用户列表")
public class User extends BaseDomain implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2199037777470546108L;

    // Long, long
    @Column(name = "ID", order = -10)
    private Long id;
    @Column(name = "longTest")
    private long longTest;

    // Integer, int
    @Column(name = "integerTest")
    private Integer integerTest;
    @Column(name = "intTest",formula = "D?+E?")
    private int intTest;

    // String
    @Column(name = "姓名", order = -9)
    private String name;

    // Character, char
    @Column(name = "characterTest")
    private Character characterTest;
    @Column(name = "ctest")
    private char ctest;

    // Short, short
    @Column(name = "shortTestUpper")
    private Short shortTestUpper;
    @Column(name = "shortTestLower")
    private short shortTestLower;

    // Fload, float
    @Column(name = "floatTestUpper")
    private Float floatTestUpper;
    @Column(name = "floatTestLower")
    private float floatTestLower;

    // Double, double
    @Column(name = "doubleTestUpper")
    private Double doubleTestUpper;
    @Column(name = "doubleTestLower")
    private double doubleTestLower;

    @Column(name = "年龄", cellType = CellType.NUMERIC, order = 1)
    private Integer age;

    // Boolean, boolean
    @Column(name = "booleanTestUpper")
    private Boolean booleanTestUpper;
    @Column(name = "中国人", cellType = CellType.BOOLEAN)
    private boolean chinese;

    // 日期
    @Column(name = "生日")
    @DateColumn(originalDateFormate = "timestamp")
    private String birthday;

}
