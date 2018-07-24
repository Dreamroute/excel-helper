package com.mook.excel.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import com.mook.excel.helper.beans.User;

public class HelperTest {

    @Test
    public void baseTest() throws Exception {

        List<User> userList = new ArrayList<>();
        Set<User> userSet = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setId(100L + i);
            user.setName("w.dehai" + i);
            user.setAge(30);
            user.setCreateTime(new Date());
            userList.add(user);
            userSet.add(user);
        }

        HSSFWorkbook workbook = ExcelHelper.create(userList);
        File outFile = new File("d:/1.xls");
        OutputStream out = new FileOutputStream(outFile);
        workbook.write(out);

        ExcelHelper.exportFile(userList, "d:/2.xls");
        ExcelHelper.exportFile(userList, new File("d:/3.xls"));
        byte[] bs = ExcelHelper.exportByteArray(userList);
        File file4 = new File("d:/4.xls");
        OutputStream os = new FileOutputStream(file4);
        os.write(bs);
        os.close();
    }

    @Test
    public void styleTest() {
        // CellFormatType formatType = CellFormatType.DATE;
        // CellType cellType = CellType.NUMERIC;
        // HSSFWorkbook workbook = new HSSFWorkbook();
        // HSSFCellStyle cellStyle = workbook.createCellStyle();
        // cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // HSSFFont font = workbook.createFont();
        // font.setColor(Font.COLOR_RED);
        // HSSFDataFormat format = workbook.createDataFormat();
        // HSSFDataFormatter f = new HSSFDataFormatter();
        //
        // //dataformat
        // HSSFDataFormat dataFormat = workbook.createDataFormat();
         HSSFDataFormat.getBuiltinFormats().forEach(System.out::println);
        System.err.println(HSSFDataFormat.getBuiltinFormat((short) 49));
        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            short format = workbook.createDataFormat().getFormat("¥#,##0");
            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("_ * #,##0_ ;_ * -#,##0_ ;_ * \"-\"_ ;_ @_ "));
            cellStyle.setFillBackgroundColor();
            HSSFCell cell = workbook.createSheet("test").createRow(0).createCell(0);
            cell.setCellStyle(cellStyle);
            cell.setCellValue("123.456023");
            File file = new File("d:/1.xls");
            OutputStream out = new FileOutputStream(file);
            workbook.write(out);
        } catch (Exception e) {
            //
            e.printStackTrace();
        }

    }

}
