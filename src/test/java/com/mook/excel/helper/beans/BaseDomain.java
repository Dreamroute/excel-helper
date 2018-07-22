package com.mook.excel.helper.beans;

import java.io.Serializable;
import java.util.Date;

public class BaseDomain implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1172774186134308106L;

    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
