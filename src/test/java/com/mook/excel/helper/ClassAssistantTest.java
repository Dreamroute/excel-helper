package com.mook.excel.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.github.dreamroute.excel.helper.annotation.Sheet;
import com.github.dreamroute.excel.helper.util.ClassAssistant;

/**
 * 
 * @author 342252328@qq.com
 *
 */
public class ClassAssistantTest {

    @Test
    public void getSheetNameTest() {
        assertEquals("A", ClassAssistant.getSheetName(Aa.class));
        assertEquals("List", ClassAssistant.getSheetName(Bb.class));
    }
    
    
    
    static class Aa {}
    
    @Sheet(name = "List")
    static class Bb{}
    
}
