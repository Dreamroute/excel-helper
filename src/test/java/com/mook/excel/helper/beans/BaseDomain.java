package com.mook.excel.helper.beans;

import com.github.dreamroute.excel.helper.annotation.Column;
import com.github.dreamroute.excel.helper.annotation.DateColumn;

import java.io.Serializable;

/**
 * 
 * @author 342252328@qq.com
 *
 */
public class BaseDomain implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1172774186134308106L;

    @Column(name = "创建时间")
    @DateColumn(originalDateFormate = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
