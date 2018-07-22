package com.mook.excel.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import com.mook.excel.helper.beans.User;
import com.mook.excel.helper.util.ExcelUtil;

public class HelperTest {

    @Test
    public void baseTest() throws Exception {
        
        List<User> userList = new ArrayList<>();
        Set<User> userSet = new HashSet<>();
        
        for (int i=0; i<3; i++) {
            User user = new User();
            user.setId(100L + i);
            user.setName("w.dehai" + i);
            user.setAge(30);
            user.setCreateTime(new Date());
            userList.add(user);
            userSet.add(user);
        }
        
        HSSFWorkbook workbook = ExcelUtil.create(userList);
        File outFile = new File("d:/1.xls");
        OutputStream out = new FileOutputStream(outFile);
        workbook.write(out);
        ExcelUtil.create(userList);
        ExcelUtil.create(userList);
        ExcelUtil.create(userSet);
        
    }
    
}






