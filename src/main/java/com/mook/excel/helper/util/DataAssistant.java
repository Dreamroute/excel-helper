package com.mook.excel.helper.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.CellType;

import com.mook.excel.helper.annotation.Column;
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
                    Object value = field.get(data);
                    // when cell is null, pre process it, avoid NPE.
                    if (Objects.isNull(value)) {
                        CellType ct = field.getAnnotation(Column.class).cellType();
                        if (ct == CellType.STRING) {
                            value = "";
                        } else if (ct == CellType.NUMERIC) {
                            value = 0;
                        } else if (ct == CellType.BOOLEAN) {
                            value = false;
                        }
                    }
                    rowData.add(value);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new ExcelHelperException(e);
                }
            }
            result.add(rowData);
        }
        return result;
    }

}
