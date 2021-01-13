package com.github.dreamroute.excel.helper;

import com.github.dreamroute.excel.helper.exception.ExcelHelperException;
import com.github.dreamroute.excel.helper.util.BaseResponse;
import com.github.dreamroute.excel.helper.util.DataAssistant;
import com.github.dreamroute.excel.helper.util.ExcelType;
import com.github.dreamroute.excel.helper.util.ExcelUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Collection;

import static com.github.dreamroute.excel.helper.util.ExcelType.XLSX;

/**
 * the root operation class, you'll use it to create or export excel files/{@link Workbook}
 *
 * @author 342252328@qq.com
 * @since JDK 1.7
 */
public class ExcelHelper {

    private ExcelHelper() {}

    /**
     * <p>
     * export as {@link Workbook}.
     * <p>
     * if you do not want to export by ExcelHelper, you can only create a workbook, then operate the workbook by yourself.
     *
     * @param type {@link ExcelType}
     * @param sheets your bussiness data.
     * @return return {@link Workbook}
     */
    public static Workbook exportWorkbook(ExcelType type, Collection<?>... sheets) {
        return ExcelUtil.create(type, sheets);
    }

    /**
     * export as a file.
     *
     * @param type {@link ExcelType}
     * @param sheets your bussiness data.
     * @param path file path
     */
    public static void exportFile(ExcelType type, Collection<?> sheets, String path) {
        exportFile(type, sheets, new File(path));
    }

    /**
     * export as a file.
     *
     * @param type {@link ExcelType}
     * @param sheets sheets your bussiness data.
     * @param newFile which file you'll write to.
     */
    public static void exportFile(ExcelType type, Collection<?> sheets, File newFile) {
        try (OutputStream out = new FileOutputStream(newFile)) {
            exportWorkbook(type, sheets).write(out);
        } catch (Exception e) {
            throw new ExcelHelperException("write to file faild." + e, e);
        }
    }

    /**
     * export to byte array.
     *
     * @param type {@link ExcelType}
     * @param sheets sheets sheets your bussiness data.
     * @return return a byte array with data.
     */
    public static byte[] exportByteArray(ExcelType type, Collection<?> sheets) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            exportWorkbook(type, sheets).write(out);
        } catch (IOException e) {
            throw new ExcelHelperException("write to file faild." + e, e);
        }
        return out.toByteArray();
    }

    /**
     * import from path.
     *
     * @param type {@link ExcelType}
     * @param path the file path
     * @param cls target Class<T>
     * @return return a {@link BaseResponse}
     */
    public static <T> BaseResponse<T> importFromPath(ExcelType type, String path, Class<T> cls) {
        File importFile = new File(path);
        if (!importFile.exists()) {
            throw new ExcelHelperException("the file " + path + " does not exist.");
        }
        return importFromFile(type, importFile, cls);
    }

    /**
     * import from file.
     *
     * @param type {@link ExcelType}
     * @param importFile the file path
     * @param cls target Class<T>
     * @return return a {@link BaseResponse}
     */
    public static <T> BaseResponse<T> importFromFile(ExcelType type, File importFile, Class<T> cls) {
        if (!importFile.exists()) {
            throw new ExcelHelperException("the file " + importFile.getName() + " does not exist.");
        }
        InputStream in;
        try {
            in = new FileInputStream(importFile);
        } catch (FileNotFoundException e) {
            throw new ExcelHelperException(e);
        }
        return importFromInputStream(type, in, cls);
    }

    /**
     * import from ByteArray
     *
     * @param type {@link ExcelType}
     * @param byteArr byte array to import.
     * @param cls target Class<T>
     * @return return a {@link BaseResponse}
     */
    public static <T> BaseResponse<T> importFromByteArray(ExcelType type, byte[] byteArr, Class<T> cls) {
        if (ArrayUtils.isEmpty(byteArr)) {
            return new BaseResponse<>(-1, "空数据", null);
        }
        InputStream in = new ByteArrayInputStream(byteArr);
        return importFromInputStream(type, in, cls);
    }

    /**
     * import from {@link InputStream}
     *
     * @param type {@link ExcelType}
     * @param inputStream {@link InputStream}
     * @param cls target Class<T>
     * @return return a {@link BaseResponse}
     */
    public static <T> BaseResponse<T> importFromInputStream(ExcelType type, InputStream inputStream, Class<T> cls) {
        BaseResponse<T> data;
        try (Workbook workbook = type == ExcelType.XLS ? new HSSFWorkbook(inputStream) : new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            data = DataAssistant.createDataFromSheet(sheet, cls);
        } catch (IOException e) {
            throw new ExcelHelperException(e);
        }
        return data;
    }

    /**
     * Spring MVC 下载
     *
     * @param data 数据
     * @param fileName 文件名
     * @param response response
     */
    public static void download(byte[] data, String fileName, HttpServletResponse response) {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename*=UTF-8''" + fileName);
            response.setHeader("Content-Length", String.valueOf(data.length));
            response.getOutputStream().write(data);
            response.flushBuffer();
        } catch (Exception e) {
            // ignore.
        }
    }

    /**
     * Spring MVC 下载
     *
     * @param data 数据
     * @param fileName 文件名
     * @param response response
     */
    public static void download(Collection<?> data, String fileName, HttpServletResponse response) {
        byte[] excel = ExcelHelper.exportByteArray(XLSX, data);
        download(excel, fileName, response);
    }

}
