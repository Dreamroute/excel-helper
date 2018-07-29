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

    @Column(name = "ID", order = -10)
    private Long id;

    @Column(name = "姓名", order = -9)
    private String name;

    @Column(name = "年龄", cellType = CellType.NUMERIC, order = 1)
    private Integer age;

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

}
