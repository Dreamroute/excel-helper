package com.github.dreamroute.excel.helper.cache;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import com.github.dreamroute.excel.helper.annotation.CellProps;
import com.github.dreamroute.excel.helper.annotation.HeaderProps;
import com.github.dreamroute.excel.helper.util.ClassAssistant;
import com.github.dreamroute.excel.helper.util.HeaderInfo;

/**
 * use to cache pojo's props
 * 
 * @author 342252328@qq.com
 *
 */
public class CacheFactory {

    private CacheFactory() {}

    private static final ConcurrentHashMap<Class<?>, String> SHEET_NAME_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class<?>, List<Field>> FIELDS_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class<?>, List<String>> HEADER_VALUES = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class<?>, Integer[]> COLUMN_WIDTH = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class<?>, CellType[]> CELL_TYPE = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class<?>, HeaderProps[]> HEADER_PROPS = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class<?>, CellProps[]> CELL_PROPS = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class<?>, Map<Integer, HeaderInfo>> HEADER_INFO = new ConcurrentHashMap<>();

    public static String findSheetName(Class<?> cls) {
        return SHEET_NAME_MAP.computeIfAbsent(cls, ClassAssistant::getSheetName);
    }

    public static List<Field> findFields(Class<?> cls) {
        return FIELDS_MAP.computeIfAbsent(cls, ClassAssistant::getAllFields);
    }

    public static List<String> findHeaderValues(Class<?> cls) {
        return HEADER_VALUES.computeIfAbsent(cls, ClassAssistant::getHeaderValues);
    }

    public static Integer[] findColumnWidth(Class<?> cls) {
        return COLUMN_WIDTH.computeIfAbsent(cls, ClassAssistant::getColumnWidth);
    }

    public static CellType[] findCellType(Class<?> cls) {
        return CELL_TYPE.computeIfAbsent(cls, ClassAssistant::getCellType);
    }

    public static HeaderProps[] findHeaderProps(Class<?> cls) {
        return HEADER_PROPS.computeIfAbsent(cls, ClassAssistant::getHeaderProps);
    }

    public static CellProps[] findCellProps(Class<?> cls) {
        return CELL_PROPS.computeIfAbsent(cls, ClassAssistant::getCellProps);
    }

    public static Map<Integer, HeaderInfo> findHeaderInfo(Class<?> cls, Row header) {
        return HEADER_INFO.computeIfAbsent(cls, clazz -> ClassAssistant.getHeaderInfo(clazz, header));
    }

}
