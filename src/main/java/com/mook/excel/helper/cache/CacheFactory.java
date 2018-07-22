package com.mook.excel.helper.cache;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.mook.excel.helper.util.ClassAssistant;

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

    public static String findSheetName(Class<?> cls) {
        String sheetName = SHEET_NAME_MAP.get(cls);
        if (StringUtils.isBlank(sheetName)) {
            sheetName = ClassAssistant.getSheetName(cls);
            SHEET_NAME_MAP.put(cls, sheetName);
        }
        return sheetName;
    }
    
    public static List<Field> findFields(Class<?> cls) {
        List<Field> fields = FIELDS_MAP.get(cls);
        if (CollectionUtils.isEmpty(fields)) {
            fields = ClassAssistant.getAllFields(cls);
            FIELDS_MAP.put(cls, fields);
        }
        return fields;
    }
    
    public static List<String> findHeaderValues(Class<?> cls) {
        List<String> headerValues = HEADER_VALUES.get(cls);
        if (CollectionUtils.isEmpty(headerValues)) {
            headerValues = ClassAssistant.getHeaderValues(cls);
            HEADER_VALUES.put(cls, headerValues);
        }
        return headerValues;
    }
    

}
