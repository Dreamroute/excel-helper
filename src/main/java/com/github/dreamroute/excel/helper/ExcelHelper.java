package com.github.dreamroute.excel.helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.github.dreamroute.excel.helper.exception.ExcelHelperException;
import com.github.dreamroute.excel.helper.util.ExcelUtil;

/**
 * the operation class, you'll use it to create or export excel files/{@link HSSFWorkbook}
 * 
 * @author 342252328@qq.com
 * @since JDK 1.7
 *
 */
public class ExcelHelper {

    private ExcelHelper() {}

    // if you do not want to export by ExcelHelper, you can only create a workbook, then operate the workbook by your self.
    public static HSSFWorkbook create(Collection<?>... sheets) {
        return ExcelUtil.create(sheets);
    }

    /**
     * export as a file.
     * 
     * @param sheets your bussiness data.
     * @param path file path
     */
    public static void exportFile(Collection<?> sheets, String path) {
        exportFile(sheets, new File(path));
    }

    /**
     * export as a file.
     * 
     * @param sheets sheets your bussiness data.
     * @param newFile which file you'll write to.
     */
    public static void exportFile(Collection<?> sheets, File newFile) {
        try (OutputStream out = new FileOutputStream(newFile)) {
            create(sheets).write(out);
        } catch (Exception e) {
            throw new ExcelHelperException("write to file faild." + e, e);
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
            throw new ExcelHelperException("write to file faild." + e, e);
        }
        return out.toByteArray();
    }

}
