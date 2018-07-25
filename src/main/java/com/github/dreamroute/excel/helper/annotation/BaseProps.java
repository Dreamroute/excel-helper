package com.github.dreamroute.excel.helper.annotation;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class BaseProps {
    private HorizontalAlignment horizontal;

    private VerticalAlignment vertical;

    public HorizontalAlignment getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(HorizontalAlignment horizontal) {
        this.horizontal = horizontal;
    }

    public VerticalAlignment getVertical() {
        return vertical;
    }

    public void setVertical(VerticalAlignment vertical) {
        this.vertical = vertical;
    }
}
