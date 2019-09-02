package com.github.dreamroute.excel.helper.util;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.github.dreamroute.excel.helper.annotation.CellProps;
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
        CellProps[] cps = CacheFactory.findCellProps(dataCls);
        CellStyle[] css = createDataCellStyle(workbook, cps);
        List<String> formulas = CacheFactory.findFormulaValues(dataCls);
        // create header row, create data rows
        createHeader(sheet, headerValues, hps, workbook);
        createData(sheet, data, cellType, css, formulas);
        setColumnWidth(sheet, columnWith);
    }

    /**
     * 根据列对应的CellProps创建对应的CellStyle数组，因为如果每个cell都创建一个CellStyle，那么导出大量数据时候会出现内存崩溃， 所以每一列对应一个CellStyle
     * 
     * @param cps <code>CellProps</code>
     * 
     */
    private static CellStyle[] createDataCellStyle(Workbook workbook, CellProps[] cps) {
        CellStyle[] css = null;
        if (cps != null && cps.length > 0) {
            css = new CellStyle[cps.length];
            for (int i = 0, len = css.length; i < len; i++) {
                CellProps cp = cps[i];
                CellStyle cs = workbook.createCellStyle();
                cs.setAlignment(cp.getHorizontal());
                cs.setVerticalAlignment(cp.getVertical());
                cs.setWrapText(true);

                String targetDateFormate = cp.getTargetDateFormate();
                if (targetDateFormate != null && targetDateFormate.trim().length() > 0) {
                    DataFormat format = workbook.createDataFormat();
                    cs.setDataFormat(format.getFormat(targetDateFormate));
                }

                css[i] = cs;
            }
        }

        return css;
    }

    private static void createHeader(Sheet sheet, List<String> headerValues, HeaderProps[] hps, Workbook workbook) {
        Row row = sheet.createRow(0);
        for (int i = 0; i < headerValues.size(); i++) {
            Cell cell = row.createCell(i);
            // Header CellType must be CellType.STRING.
            cell.setCellType(CellType.STRING);
            cell.setCellValue(headerValues.get(i));

            // cell style
            CellStyle cellStyle = workbook.createCellStyle();
            processHeaderCellStyle(cellStyle, hps[i]);
            cell.setCellStyle(cellStyle);
        }
    }

    private static void createData(Sheet sheet, List<List<Object>> data, CellType[] cellType, CellStyle[] css, List<String> formulaValues) {
        for (int i = 0; i < data.size(); i++) {
            // 0 row is header, data row from 1.
            Row row = sheet.createRow(i + 1);
            List<Object> rowData = data.get(i);
            for (int j = 0; j < rowData.size(); j++) {
                Cell cell = row.createCell(j);
                cell.setCellType(cellType[j]);
                cell.setCellStyle(css[j]);
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
                    // 单独处理日期类型
                    Object d = rowData.get(j);
                    if (d instanceof Date) {
                        Date dt = (Date) d;
                        cell.setCellValue(dt);
                    } else {
                        cell.setCellValue(rowData.get(j).toString());
                    }
                }

                // 设置公式
                if (StringUtils.isNotBlank(formulaValues.get(j))) {
                    cell.setCellFormula(formulaValues.get(j).replace("?", (i + 2) + ""));
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
    private static void processHeaderCellStyle(CellStyle cs, HeaderProps headerProps) {
        cs.setAlignment(headerProps.getHorizontal());
        cs.setVerticalAlignment(headerProps.getVertical());
    }

    private static void setColumnWidth(Sheet sheet, Integer[] columnWith) {
        for (int i = 0; i < columnWith.length; i++) {
            // for xlsx, before autoSizeColumn() method, must invoke trackAllColumnsForAutoSizing, otherwise every column's with will be the same.
            if (SXSSFSheet.class.isAssignableFrom(sheet.getClass())) {
                SXSSFSheet st = (SXSSFSheet) sheet;
                st.trackAllColumnsForAutoSizing();
            }

            sheet.autoSizeColumn(i);
            // with = with + 200
            int width = columnWith[i] > 0 ? columnWith[i] : sheet.getColumnWidth(i) + 200;
            sheet.setColumnWidth(i, width);
        }
    }

    private static String getSheetName(Collection<?> sheetData) {
        Class<?> dataClass = sheetData.iterator().next().getClass();
        return CacheFactory.findSheetName(dataClass);
    }

}
