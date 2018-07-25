package com.mook.excel.helper.beans;

import java.io.Serializable;

import com.mook.excel.helper.annotation.Column;

public class BaseDomain implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1172774186134308106L;

    @Column(name = "创建时间")
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
