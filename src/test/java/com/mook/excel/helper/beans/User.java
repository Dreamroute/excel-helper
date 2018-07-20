package com.mook.excel.helper.beans;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2199037777470546108L;

    private Long id;
    private String name;
    private Integer age;
    private Date birthday;

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

}
