package com.mook.excel.helper;

import com.github.dreamroute.excel.helper.ExcelHelper;
import com.github.dreamroute.excel.helper.annotation.Column;
import com.github.dreamroute.excel.helper.annotation.DateColumn;
import com.github.dreamroute.excel.helper.annotation.Sheet;
import com.github.dreamroute.excel.helper.util.BaseResponse;
import com.github.dreamroute.excel.helper.util.ExcelType;
import com.mook.excel.helper.beans.User;
import lombok.Data;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 342252328@qq.com
 */
public class HelperTest {

    @Test
    public void baseTest() {

        try {
            ExcelType type = ExcelType.XLSX;
            
            List<User> userList = new ArrayList<>();
            Set<User> userSet = new HashSet<>();
            
            for (int i = 0, len = 10; i < len; i++) {
                User user = new User();
                user.setId(100L + i);
                user.setLongTest(15L);
                
                user.setIntegerTest(new Integer(2 + i));
                
                user.setName("w.dehai" + i);
                
                user.setCharacterTest(new Character('A'));
                user.setCtest('a');
                
                user.setShortTestUpper(new Short("6"));
                user.setShortTestLower((short) 66);
                
                user.setFloatTestUpper(new Float(1.23));
                user.setFloatTestLower(3.14f);
                
                user.setDoubleTestUpper(new Double("333"));
                user.setDoubleTestLower(12.222);
                
                user.setAge(300000000);
                
                user.setBooleanTestUpper(Boolean.FALSE);
                user.setChinese(true);
                
                user.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(new Date().getTime() + i * 1000)));

                user.setBirthday(System.currentTimeMillis() + "");
                
                userList.add(user);
                userSet.add(user);

            }
            
            StopWatch watch = new StopWatch();
            watch.start();
            // 在windows平台测试，如果是mac需要修改成mac类型的路径
            Workbook workbook = ExcelHelper.exportWorkbook(type, userList);
            String path1 = type == ExcelType.XLS ? "d:/1.xls" : "d:/1.xlsx";
            File outFile = new File(path1);
            OutputStream out = new FileOutputStream(outFile);
            workbook.write(out);
            out.close();
            watch.stop();
            System.err.println(watch.getTime());
            
            watch.reset();
            watch.start();
            String path2 = type == ExcelType.XLS ? "d:/2.xls" : "d:/2.xlsx";
            ExcelHelper.exportFile(type, path2, userList);
            watch.stop();
            System.err.println(watch.getTime());
            
            watch.reset();
            watch.start();
            String path3 = type == ExcelType.XLS ? "d:/3.xls" : "d:/3.xlsx";
            ExcelHelper.exportFile(type, new File(path3), userList);
            System.err.println(watch.getTime());
            
            watch.reset();
            watch.start();
            String path4 = type == ExcelType.XLS ? "d:/4.xls" : "d:/4.xlsx";
            File file4 = new File(path4);
            OutputStream os = new FileOutputStream(file4);
            byte[] bs = ExcelHelper.exportByteArray(type, userList);
            os.write(bs);
            os.close();
            System.err.println(watch.getTime());
            System.out.println("OK");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void styleTest() {
        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            HSSFCellStyle cs = workbook.createCellStyle();
            cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cs.setFillBackgroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
            cs.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_GREEN.getIndex());
            HSSFCell cell = workbook.createSheet("test").createRow(0).createCell(0);
            cell.setCellStyle(cs);
            cell.setCellType(CellType.STRING);
            cell.setCellValue("wangdehai");
            File file = new File("d:/1.xls");
            OutputStream out = new FileOutputStream(file);
            workbook.write(out);
        } catch (Exception e) {
            //
            e.printStackTrace();
        }
    }

    @Test
    public void importTest() {
        String path = "d:/1.xlsx";
        ExcelType type = ExcelType.XLSX;
        BaseResponse<User> response = ExcelHelper.importFromPath(type, path, User.class);
        List<User> users = response.getData();
        System.err.println(users);
    }
    

    @Test
    public void sortListTest() {
        List<Demo> demoList = new ArrayList<>();
        for (int i = 0, len = 10; i < len; i++) {
            Demo demo = new Demo();
            demo.name = "w.dehai";
            demo.height = new BigDecimal("1.80342");
            demoList.add(demo);
        }
        ExcelHelper.exportFile(ExcelType.XLS, "d:/DemoList.xls", demoList);
    }

    @Data
    @Sheet(name = "DemoList")
    static class Demo {
        @Column(name = "姓名")
        private String name;
        @Column(name = "身高", cellType = CellType.NUMERIC)
        private BigDecimal height;
    }

    @Data
    @Sheet(name = "dateDemo")
    static class Datedemo {
        @Column(name = "姓名")
        private String name;
        @Column(name = "时间")
        @DateColumn
        private long time;
    }

    @Test
    public void dateDemoTest() {
        Datedemo dd = new Datedemo();
        dd.setName("w.dehai");
        dd.setTime(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

        Datedemo d2 = new Datedemo();
        dd.setTime(new Date().getTime());
        List<Datedemo> ds = new ArrayList<>();
        ds.add(dd);
        ds.add(d2);

        ExcelHelper.exportFile(ExcelType.XLSX, "d:/ss.xlsx", ds);
    }

    @Test
    public void mm() {
        long value = 1610553088;
        Timestamp from = Timestamp.from(Instant.ofEpochMilli(Long.valueOf(value)));
        String format = from.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.err.println(format);
    }

}
