package com.github.dreamroute.excel.helper.util;

import java.util.Collection;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.github.dreamroute.excel.helper.annotation.BaseProps;
import com.github.dreamroute.excel.helper.annotation.HeaderProps;
import com.github.dreamroute.excel.helper.cache.CacheFactory;

/**
 * Export workbook util
 * 
 * @author 342252328@qq.com
 *
 */
public final class ExcelUtil {
    
    private ExcelUtil() {}
    
    private static CellStyle cellStyle;
    
    /**
     * export as a {@link Workbook}, maybe include one or more sheet.
     * 
     * @param sheets it's a array, every Collection will create a sheet.
     * @return return a {@link Workbook}
     */
    public static Workbook create(ExcelType type, Collection<?>... sheets) {
        Workbook workBook;
        if (type == ExcelType.XLS) {
            workBook = new HSSFWorkbook();
        } else {
            workBook = new SXSSFWorkbook();
        }
        
        /**cell style统一设置，否则会出现创建cellStyle过多框架不支持问题**/
        if (cellStyle == null) {
            cellStyle = workBook.createCellStyle();
            cellStyle.setWrapText(true);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        
        createWorkbook(workBook, sheets);
        return workBook;
    }

    private static void createWorkbook(Workbook workBook, Collection<?>... sheets) {
        for (Collection<?> sheetData : sheets) {
            if (CollectionUtils.isNotEmpty(sheetData)) {
                createSheet(workBook, sheetData);
            }
        }
    }

    private static void createSheet(Workbook workbook, Collection<?> sheetData) {
        String sheetName = getSheetName(sheetData);
        Sheet sheet = workbook.createSheet(sheetName);
        Class<?> dataCls = sheetData.iterator().next().getClass();

        // excel content (header + data)
        List<String> headerValues = CacheFactory.findHeaderValues(dataCls);
        List<List<Object>> data = DataAssistant.createData(sheetData);
        CellType[] cellType = CacheFactory.findCellType(dataCls);
        Integer[] columnWith = CacheFactory.findColumnWidth(dataCls);
        HeaderProps[] hps = CacheFactory.findHeaderProps(dataCls);
//        CellProps[] cps = CacheFactory.findCellProps(dataCls);
        List<String> formulaValues=CacheFactory.findFormulaValues(dataCls);
        // create header row, create data rows
        createHeader(sheet, headerValues, hps, workbook);
        createData(sheet, data, cellType, workbook,formulaValues);
        setColumnWidth(sheet, columnWith);
    }

    private static void createHeader(Sheet sheet, List<String> headerValues, HeaderProps[] hps, Workbook workbook) {
        Row row = sheet.createRow(0);
        for (int i = 0; i < headerValues.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headerValues.get(i));

            // cell style
            CellStyle cellStyle = workbook.createCellStyle();
            processCellStyle(cellStyle, hps[i]);
            cell.setCellStyle(cellStyle);
        }
    }

    private static void createData(Sheet sheet, List<List<Object>> data, CellType[] cellType, Workbook workbook,List<String> formulaValues) {
        for (int i = 0; i < data.size(); i++) {
            // 0 row is header, data row from 1.
            Row row = sheet.createRow(i + 1);
            List<Object> rowData = data.get(i);
            for (int j = 0; j < rowData.size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellType(cellType[j]);
                if (cellType[j] == CellType.NUMERIC) {
                    String v = rowData.get(j).toString();
                    try {
                        cell.setCellValue(Double.parseDouble(v));
                    } catch (NumberFormatException e) {
                        // 这里处理比较极端，转换成Double类型失败时，不抛出异常，而是将String类型写入
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(v);
                    }
                } else if (cellType[j] == CellType.BOOLEAN) {
                    cell.setCellValue(Boolean.parseBoolean(rowData.get(j).toString()));
                } else {
                    cell.setCellValue(rowData.get(j).toString());
                }

                // cell style
                cell.setCellStyle(cellStyle);
                //设置公式
                if (StringUtils.isNotBlank(formulaValues.get(j))) {
                    cell.setCellFormula(formulaValues.get(j).replace("?",(i+2)+""));
                }
            }
        }
    }

    /**
     * process style, @Header and @Cell common props.
     * 
     * @param cs
     * @param baseProps
     */
    private static void processCellStyle(CellStyle cs, BaseProps baseProps) {
        cs.setAlignment(baseProps.getHorizontal());
        cs.setVerticalAlignment(baseProps.getVertical());
    }

    private static void setColumnWidth(Sheet sheet, Integer[] columnWith) {
        for (int i = 0; i < columnWith.length; i++) {
            // for xlsx, before autoSizeColumn() method, must invoke trackAllColumnsForAutoSizing, otherwise every column's with will be the same.
            if (SXSSFSheet.class.isAssignableFrom(sheet.getClass())) {
                SXSSFSheet st = (SXSSFSheet) sheet;
                st.trackAllColumnsForAutoSizing();
            }

            sheet.autoSizeColumn(i);
            // 1.3 times
            int width = columnWith[i] > 0 ? columnWith[i] : sheet.getColumnWidth(i) * 13 / 10;
            sheet.setColumnWidth(i, width);
        }
    }

    private static String getSheetName(Collection<?> sheetData) {
        Class<?> dataClass = sheetData.iterator().next().getClass();
        return CacheFactory.findSheetName(dataClass);
    }

}
