package com.github.dreamroute.excel.helper.util;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

import com.github.dreamroute.excel.helper.annotation.BaseProps;
import com.github.dreamroute.excel.helper.annotation.CellProps;
import com.github.dreamroute.excel.helper.annotation.HeaderProps;
import com.github.dreamroute.excel.helper.cache.CacheFactory;
import com.github.dreamroute.excel.helper.exception.ExcelHelperException;

/**
 * Export workbook util
 * 
 * @author 342252328@qq.com
 *
 */
public final class ExcelUtil {

    private ExcelUtil() {}

    /**
     * export as a HSSFWorkbook, maybe include one or more sheet.
     * 
     * @param sheets it's a array, every Collection will create a sheet.
     * @return return a {@link HSSFWorkbook}
     */
    public static HSSFWorkbook create(Collection<?>... sheets) {
        if (ArrayUtils.isEmpty(sheets))
            return new HSSFWorkbook();
        HSSFWorkbook workbook = createWorkbook(sheets);
        try {
            workbook.close();
        } catch (IOException e) {
            throw new ExcelHelperException("close workbook faild." + e, e);
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
        List<List<Object>> data = DataAssistant.createData(sheetData);
        CellType[] cellType = CacheFactory.findCellType(dataCls);
        Integer[] columnWith = CacheFactory.findColumnWidth(dataCls);
        HeaderProps[] hps = CacheFactory.findHeaderProps(dataCls);
        CellProps[] cps = CacheFactory.findCellProps(dataCls);

        // create header row, create data rows
        createHeaderRow(sheet, headerValues, hps, workbook);
        createDataRows(sheet, data, cellType, cps, workbook);
        setColumnWidth(sheet, columnWith);
    }

    private static void createHeaderRow(HSSFSheet sheet, List<String> headerValues, HeaderProps[] hps, HSSFWorkbook workbook) {
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headerValues.size(); i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(headerValues.get(i));

            // cell style
            HSSFCellStyle hcs = workbook.createCellStyle();
            processCellStyle(hcs, hps[i]);
            cell.setCellStyle(hcs);
        }
    }

    private static void createDataRows(HSSFSheet sheet, List<List<Object>> data, CellType[] cellType, CellProps[] cps, HSSFWorkbook workbook) {
        for (int i = 0; i < data.size(); i++) {
            HSSFRow row = sheet.createRow(i + 1); // 0 row is header, data row from 1.
            List<Object> rowData = data.get(i);
            for (int j = 0; j < rowData.size(); j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellType(cellType[j]);
                if (cellType[j] == CellType.NUMERIC) {
                    cell.setCellValue(Double.parseDouble(rowData.get(j).toString()));
                } else if (cellType[j] == CellType.BOOLEAN) {
                    cell.setCellValue(Boolean.parseBoolean(rowData.get(j).toString()));
                } else {
                    cell.setCellValue(rowData.get(j).toString());
                }

                // cell style
                HSSFCellStyle hcs = workbook.createCellStyle();
                processCellStyle(hcs, cps[j]);
                cell.setCellStyle(hcs);
            }
        }
    }

    // @Header and @Cell common props.
    private static void processCellStyle(HSSFCellStyle hcs, BaseProps baseProps) {
        hcs.setAlignment(baseProps.getHorizontal());
        hcs.setVerticalAlignment(baseProps.getVertical());
    }

    private static void setColumnWidth(HSSFSheet sheet, Integer[] columnWith) {
        for (int i = 0; i < columnWith.length; i++) {
            sheet.autoSizeColumn(i);
            int width = columnWith[i] > 0 ? columnWith[i] : sheet.getColumnWidth(i) * 13 / 10; // 1.3 times
            sheet.setColumnWidth(i, width);
        }
    }

    private static String getSheetName(Collection<?> sheetData) {
        Class<?> dataClass = sheetData.iterator().next().getClass();
        return CacheFactory.findSheetName(dataClass);
    }

}
