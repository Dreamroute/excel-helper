package com.github.dreamroute.excel.helper.annotation;

/**
 * 
 * @author 342252328@qq.com
 *
 */
public class CellProps extends BaseProps {

    private String originalDateFormate;
    private String targetDateFormate;

    /**
     * @return the originalDateFormate
     */
    public String getOriginalDateFormate() {
        return originalDateFormate;
    }

    /**
     * @param originalDateFormate the originalDateFormate to set
     */
    public void setOriginalDateFormate(String originalDateFormate) {
        this.originalDateFormate = originalDateFormate;
    }

    /**
     * @return the targetDateFormate
     */
    public String getTargetDateFormate() {
        return targetDateFormate;
    }

    /**
     * @param targetDateFormate the targetDateFormate to set
     */
    public void setTargetDateFormate(String targetDateFormate) {
        this.targetDateFormate = targetDateFormate;
    }

}
