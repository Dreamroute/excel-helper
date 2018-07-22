package com.mook.excel.helper.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.mook.excel.helper.cache.CacheFactory;
import com.mook.excel.helper.exception.ExcelHelperException;

public class DataAssistant {

    private DataAssistant() {}

    public static List<List<Object>> createData(Collection<?> sheetData) {

        List<List<Object>> result = new ArrayList<>();

        Class<?> dataCls = sheetData.iterator().next().getClass();
        List<Field> fields = CacheFactory.findFields(dataCls);
        Iterator<?> dataIterator = sheetData.iterator();
        while (dataIterator.hasNext()) {
            List<Object> rowData = new ArrayList<>(fields.size());
            Object data = dataIterator.next();
            for (Field field : fields) {
                try {
                    rowData.add(field.get(data));
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new ExcelHelperException(e);
                }
            }
            result.add(rowData);
        }
        return result;
    }

}
