package com.mook.excel.helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.mook.excel.helper.exception.ExcelHelperException;
import com.mook.excel.helper.util.ExcelUtil;

public class ExcelHelper {

    private ExcelHelper() {}

    // 如果不希望使用工具的导出方案，可以获取到workbook进行自定义
    public static HSSFWorkbook create(Collection<?>... sheets) {
        return ExcelUtil.create(sheets);
    }

    /**
     * export to file.
     * 
     * @param sheets your bussiness data.
     * @param path file path
     */
    public static void exportFile(Collection<?> sheets, String path) {
        exportFile(sheets, new File(path));
    }

    /**
     * export to file.
     * 
     * @param sheets sheets your bussiness data.
     * @param newFile
     */
    public static void exportFile(Collection<?> sheets, File newFile) {
        try (OutputStream out = new FileOutputStream(newFile)) {
            create(sheets).write(out);
        } catch (Exception e) {
            throw new ExcelHelperException("" + e, e);
        }
    }

    /**
     * export to byte array.
     * 
     * @param sheets sheets sheets your bussiness data.
     * @return return a byte array with data.
     */
    public static byte[] exportByteArray(Collection<?> sheets) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            create(sheets).write(out);
        } catch (IOException e) {
            throw new ExcelHelperException("" + e, e);
        }
        return out.toByteArray();
    }

}
