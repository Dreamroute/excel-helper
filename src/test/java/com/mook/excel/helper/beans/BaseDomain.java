package com.mook.excel.helper.beans;

import java.io.Serializable;
import java.util.Date;

import com.mook.excel.helper.annotation.Cell;

public class BaseDomain implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1172774186134308106L;

    @Cell(name = "创建时间")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
