package com.mook.excel.helper;

import java.io.IOException;
import java.util.Collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.mook.excel.helper.annotation.Sheet;
import com.mook.excel.helper.exception.ExcelHelperException;

/**
 * 导出Excel操作类
 * 
 * @author w.dehai
 *
 */
public final class ExcelHelper {

    private ExcelHelper() {}

    /**
     * 导出成为一个HSSFWorkbook，可包含多个sheet
     * 
     * @param sheets 数组，每个Collection会生成一个sheet
     * @return 返回一个HSSFWorkbook
     */
    public static HSSFWorkbook export(Collection<?>... sheets) {
        if (ArrayUtils.isEmpty(sheets))
            return new HSSFWorkbook();
        HSSFWorkbook workbook = createWorkbook(sheets);
        try {
            workbook.close();
        } catch (IOException e) {
            throw new ExcelHelperException(e);
        }
        return workbook;
    }

    private static HSSFWorkbook createWorkbook(Collection<?>... sheets) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        for (Collection<?> sheet : sheets) {
            if (CollectionUtils.isNotEmpty(sheet)) {
                createSheet(workbook, sheet);
            }
        }
        return workbook;
    }

    private static void createSheet(HSSFWorkbook workbook, Collection<?> sheetData) {
        String sheetName = getSheetName(sheetData);
        HSSFSheet sheet = workbook.createSheet(sheetName);
    }

    private static String getSheetName(Collection<?> sheetData) {
        Class<?> dataClass = sheetData.stream().findFirst().get().getClass();
        String sheetName = ClassUtils.getSimpleName(dataClass);
        if (dataClass.isAnnotationPresent(Sheet.class)) {
            Sheet sheetAnno = dataClass.getAnnotation(Sheet.class);
            sheetName = sheetAnno.name();
        }
        return sheetName;
    }

}
