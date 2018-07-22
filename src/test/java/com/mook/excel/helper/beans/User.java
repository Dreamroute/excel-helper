package com.mook.excel.helper.beans;

import java.io.Serializable;

import com.mook.excel.helper.annotation.Cell;
import com.mook.excel.helper.annotation.Sheet;

@Sheet(name = "用户列表")
public class User extends BaseDomain implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2199037777470546108L;

    @Cell(name = "ID")
    private Long id;
    
    @Cell(name = "姓名")
    private String name;
    
    @Cell(name = "年龄")
    private Integer age;

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

}
