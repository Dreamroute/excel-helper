package com.mook.excel.helper;

import java.util.ArrayList;

import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

public class HelperTest {

    @Test
    public void baseTest() {
        ExcelHelper.export(new ArrayList<>());
    }
}
