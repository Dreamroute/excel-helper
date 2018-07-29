package com.mook.excel.helper.beans;

import java.io.Serializable;

import org.apache.poi.ss.usermodel.CellType;

import com.github.dreamroute.excel.helper.annotation.Column;
import com.github.dreamroute.excel.helper.annotation.Sheet;

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
    @Column(name = "intTest")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isChinese() {
        return chinese;
    }

    public void setChinese(boolean chinese) {
        this.chinese = chinese;
    }

    public long getLongTest() {
        return longTest;
    }

    public void setLongTest(long longTest) {
        this.longTest = longTest;
    }

    public Integer getIntegerTest() {
        return integerTest;
    }

    public void setIntegerTest(Integer integerTest) {
        this.integerTest = integerTest;
    }

    public int getIntTest() {
        return intTest;
    }

    public void setIntTest(int intTest) {
        this.intTest = intTest;
    }

    public Character getCharacterTest() {
        return characterTest;
    }

    public void setCharacterTest(Character characterTest) {
        this.characterTest = characterTest;
    }

    public Short getShortTestUpper() {
        return shortTestUpper;
    }

    public void setShortTestUpper(Short shortTestUpper) {
        this.shortTestUpper = shortTestUpper;
    }

    public short getShortTestLower() {
        return shortTestLower;
    }

    public void setShortTestLower(short shortTestLower) {
        this.shortTestLower = shortTestLower;
    }

    public Float getFloatTestUpper() {
        return floatTestUpper;
    }

    public void setFloatTestUpper(Float floatTestUpper) {
        this.floatTestUpper = floatTestUpper;
    }

    public float getFloatTestLower() {
        return floatTestLower;
    }

    public void setFloatTestLower(float floatTestLower) {
        this.floatTestLower = floatTestLower;
    }

    public Double getDoubleTestUpper() {
        return doubleTestUpper;
    }

    public void setDoubleTestUpper(Double doubleTestUpper) {
        this.doubleTestUpper = doubleTestUpper;
    }

    public double getDoubleTestLower() {
        return doubleTestLower;
    }

    public void setDoubleTestLower(double doubleTestLower) {
        this.doubleTestLower = doubleTestLower;
    }

    public Boolean getBooleanTestUpper() {
        return booleanTestUpper;
    }

    public void setBooleanTestUpper(Boolean booleanTestUpper) {
        this.booleanTestUpper = booleanTestUpper;
    }

    public char getCtest() {
        return ctest;
    }

    public void setCtest(char ctest) {
        this.ctest = ctest;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", longTest=" + longTest + ", integerTest=" + integerTest + ", intTest=" + intTest + ", name=" + name + ", characterTest=" + characterTest + ", ctest=" + ctest
                + ", shortTestUpper=" + shortTestUpper + ", shortTestLower=" + shortTestLower + ", floatTestUpper=" + floatTestUpper + ", floatTestLower=" + floatTestLower + ", doubleTestUpper="
                + doubleTestUpper + ", doubleTestLower=" + doubleTestLower + ", age=" + age + ", booleanTestUpper=" + booleanTestUpper + ", chinese=" + chinese + "]";
    }

}
