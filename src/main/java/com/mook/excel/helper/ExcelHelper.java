package com.mook.excel.helper;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.mook.excel.helper.cache.CacheFactory;
import com.mook.excel.helper.exception.ExcelHelperException;

/**
 * 导出Excel操作类
 * 
 * @author 342252328@qq.com
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
    public static HSSFWorkbook create(Collection<?>... sheets) {
        if (ArrayUtils.isEmpty(sheets))
            return new HSSFWorkbook();
        HSSFWorkbook workbook = createWorkbook(sheets);
        try {
            workbook.close();
        } catch (IOException e) {
            throw new ExcelHelperException("workbook关闭失败" + e, e);
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
        Class<?> dataCls = sheetData.iterator().next().getClass();

        // excel content (header + data)
        List<String> headerValues = CacheFactory.findHeaderValues(dataCls);

        // create header row, create data rows
        createHeaderRow(sheet, headerValues);
        // creatDataRows(sheet, allFields);
    }

    private static void createHeaderRow(HSSFSheet sheet, List<String> headerValues) {
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headerValues.size(); i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(headerValues.get(i));
        }
    }

    private static void creatDataRows(HSSFSheet sheet, List<Field> allFields) {
        // TODO Auto-generated method stub

    }

    private static String getSheetName(Collection<?> sheetData) {
        Class<?> dataClass = sheetData.iterator().next().getClass();
        return CacheFactory.findSheetName(dataClass);
    }

}
