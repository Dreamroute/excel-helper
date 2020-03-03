package com.github.dreamroute.excel.helper.util;

import java.lang.reflect.Field;
import java.lang.reflect.ReflectPermission;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import com.github.dreamroute.excel.helper.annotation.Cell;
import com.github.dreamroute.excel.helper.annotation.CellProps;
import com.github.dreamroute.excel.helper.annotation.Column;
import com.github.dreamroute.excel.helper.annotation.DateColumn;
import com.github.dreamroute.excel.helper.annotation.Header;
import com.github.dreamroute.excel.helper.annotation.HeaderProps;
import com.github.dreamroute.excel.helper.annotation.PropsAnno;
import com.github.dreamroute.excel.helper.annotation.Sheet;
import com.github.dreamroute.excel.helper.cache.CacheFactory;
import com.github.dreamroute.excel.helper.exception.ExcelHelperException;

/**
 * util class
 *
 * @author 342252328@qq.com
 */
public final class ClassAssistant {

    private ClassAssistant() {}

    public static String getSheetName(Class<?> cls) {
        String sheetName = ClassUtils.getSimpleName(cls);
        if (cls.isAnnotationPresent(Sheet.class)) {
            Sheet sheetAnno = cls.getAnnotation(Sheet.class);
            sheetName = sheetAnno.name();
        }
        return sheetName;
    }

    public static List<String> getHeaderValues(Class<?> cls) {
        List<Field> fields = CacheFactory.findFields(cls);
        List<String> headerValues = new ArrayList<>();
        for (Field field : fields) {
            headerValues.add(field.getAnnotation(Column.class).name());
        }
        return headerValues;
    }

    public static List<Field> getAllFields(Class<?> dataCls) {
        List<Class<?>> superClsList = ClassUtils.getAllSuperclasses(dataCls);
        // add myself
        superClsList.add(dataCls);
        List<Field> fields = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(superClsList)) {
            fields = new ArrayList<>();
            for (Class<?> superCls : superClsList) {
                if (!Objects.equals(superCls, Object.class)) {
                    addFields(superCls, fields);
                }
            }
        }
        sortFields(fields);
        return fields;
    }

    private static void addFields(Class<?> clazz, List<Field> fieldList) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (canAccessPrivateMethods()) {
                try {
                    field.setAccessible(true);
                } catch (Exception e) {
                    // ignored.
                }
            }
            if (field.isAccessible() && !fieldList.contains(field) && field.isAnnotationPresent(Column.class)) {
                fieldList.add(field);
            }
        }
    }

    private static void sortFields(List<Field> fields) {
        Column col = fields.get(0).getAnnotation(Column.class);
        col.order();
        fields.sort(new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                int order1 = o1.getAnnotation(Column.class).order();
                int order2 = o2.getAnnotation(Column.class).order();
                int lessThan = order1 == order2 ? 0 : -1;
                return order1 > order2 ? 1 : lessThan;
            }
        });
    }

    private static boolean canAccessPrivateMethods() {
        try {
            SecurityManager securityManager = System.getSecurityManager();
            if (null != securityManager) {
                securityManager.checkPermission(new ReflectPermission("suppressAccessChecks"));
            }
        } catch (SecurityException e) {
            return false;
        }
        return true;
    }

    public static Integer[] getColumnWidth(Class<?> cls) {
        List<Field> fields = CacheFactory.findFields(cls);
        Integer[] columnWidth = new Integer[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            int width = fields.get(i).getAnnotation(Column.class).width();
            columnWidth[i] = Math.max(width, 0);
        }
        return columnWidth;
    }

    public static CellType[] getCellType(Class<?> cls) {
        List<Field> fields = CacheFactory.findFields(cls);
        CellType[] cellType = new CellType[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            cellType[i] = fields.get(i).getAnnotation(Column.class).cellType();
        }
        return cellType;
    }

    public static HeaderProps[] getHeaderProps(Class<?> cls) {
        List<Field> fields = CacheFactory.findFields(cls);
        HeaderProps[] hps = new HeaderProps[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            Header anno = fields.get(i).getAnnotation(Header.class);
            try {
                anno = anno == null ? PropsAnno.class.getDeclaredField("props").getAnnotation(Header.class) : anno;
            } catch (NoSuchFieldException | SecurityException e) {
                throw new ExcelHelperException(e);
            }
            HeaderProps hp = new HeaderProps();
            hp.setHorizontal(anno.horizontal());
            hp.setVertical(anno.vertical());
            hps[i] = hp;
        }
        return hps;
    }

    public static CellProps[] getCellProps(Class<?> cls) {
        List<Field> fields = CacheFactory.findFields(cls);
        CellProps[] hps = new CellProps[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            Cell anno = field.getAnnotation(Cell.class);
            CellProps cp = new CellProps();
            try {
                anno = anno == null ? PropsAnno.class.getDeclaredField("props").getAnnotation(Cell.class) : anno;
                cp.setHorizontal(anno.horizontal());
                cp.setVertical(anno.vertical());
                
                if (field.isAnnotationPresent(DateColumn.class)) {
                    DateColumn dc = field.getAnnotation(DateColumn.class);
                    cp.setOriginalDateFormate(dc.originalDateFormate());
                    cp.setTargetDateFormate(dc.targetDateFormate());
                }
                
                hps[i] = cp;
            } catch (NoSuchFieldException | SecurityException e) {
                throw new ExcelHelperException(e);
            }
        }
        return hps;
    }

    public static Map<Integer, HeaderInfo> getHeaderInfo(Class<?> cls, Row header) {
        Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = header.cellIterator();
        List<Field> fields = ClassAssistant.getAllFields(cls);
        Map<Integer, HeaderInfo> headerInfo = new HashMap<>(fields.size());
        while (cellIterator.hasNext()) {
            org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
            String headerName = cell.getStringCellValue();
            for (Field field : fields) {
                Column col = field.getAnnotation(Column.class);
                String name = col.name();
                if (Objects.equals(headerName, name)) {
                    HeaderInfo hi = new HeaderInfo(col.cellType(), field);
                    headerInfo.put(cell.getColumnIndex(), hi);
                    break;
                }
            }
        }

        // 检查表头与实体对象是否能够一一对应
        if (!Objects.equals(fields.size(), headerInfo.size())) {
            throw new ExcelHelperException("你上传的Excel文件的列与目标对象的列存在差异");
        }

        return headerInfo;
    }

    public static List<String> getFormulaValues(Class<?> cls) {
        List<Field> fields = CacheFactory.findFields(cls);
        List<String> formulaValues = new ArrayList<>();
        for (Field field : fields) {
            formulaValues.add(field.getAnnotation(Column.class).formula());
        }
        return formulaValues;
    }

}
