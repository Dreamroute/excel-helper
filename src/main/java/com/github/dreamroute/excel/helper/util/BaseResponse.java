package com.github.dreamroute.excel.helper.util;

import java.io.Serializable;
import java.util.List;

/**
 * 返回内容包装类
 *
 * @author: niechen
 * @date: 2019/6/19
 **/
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = -6585432370633841612L;
    /**
     * 响应代码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 响应内容
     */
    private List<T> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public BaseResponse() {
    }

    public BaseResponse(Integer code, String msg, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse(List<T> data) {
        this.code = 0;
        this.msg = "响应正确";
        this.data = data;
    }
}
