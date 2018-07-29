package com.github.dreamroute.excel.helper.util;

import java.lang.reflect.Field;
import java.lang.reflect.ReflectPermission;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.poi.ss.usermodel.CellType;

import com.github.dreamroute.excel.helper.annotation.Cell;
import com.github.dreamroute.excel.helper.annotation.CellProps;
import com.github.dreamroute.excel.helper.annotation.Column;
import com.github.dreamroute.excel.helper.annotation.Header;
import com.github.dreamroute.excel.helper.annotation.HeaderProps;
import com.github.dreamroute.excel.helper.annotation.PropsAnno;
import com.github.dreamroute.excel.helper.annotation.Sheet;
import com.github.dreamroute.excel.helper.cache.CacheFactory;

/**
 * util class
 * 
 * @author 342252328@qq.com
 *
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
        for (int i = 0; i < fields.size(); i++)
            headerValues.add(fields.get(i).getAnnotation(Column.class).name());
        return headerValues;
    }

    public static List<Field> getAllFields(Class<?> dataCls) {
        List<Class<?>> superClsList = ClassUtils.getAllSuperclasses(dataCls);
        superClsList.add(dataCls); // add myself
        List<Field> fields = null;
        if (CollectionUtils.isNotEmpty(superClsList)) {
            fields = new ArrayList<>();
            for (Class<?> superCls : superClsList)
                addFields(superCls, fields);
        }
        
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
        
        sortFields(fieldList);
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
            columnWidth[i] = width > 0 ? width : 0;
        }
        return columnWidth;
    }

    public static CellType[] getCellType(Class<?> cls) {
        List<Field> fields = CacheFactory.findFields(cls);
        CellType[] cellType = new CellType[fields.size()];
        for (int i = 0; i < fields.size(); i++)
            cellType[i] = fields.get(i).getAnnotation(Column.class).cellType();
        return cellType;
    }

    public static HeaderProps[] getHeaderProps(Class<?> cls) {
        List<Field> fields = CacheFactory.findFields(cls);
        HeaderProps[] hps = new HeaderProps[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            Header anno = fields.get(i).getAnnotation(Header.class);
            HeaderProps hp = new HeaderProps();
            try {
                anno = anno == null ? PropsAnno.class.getDeclaredField("props").getAnnotation(Header.class) : anno;
                hp.setHorizontal(anno.horizontal());
                hp.setVertical(anno.vertical());
                hps[i] = hp;
            } catch (NoSuchFieldException | SecurityException e) {
                // ignore.
            }
        }
        return hps;
    }

    public static CellProps[] getCellProps(Class<?> cls) {
        List<Field> fields = CacheFactory.findFields(cls);
        CellProps[] hps = new CellProps[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            Cell anno = fields.get(i).getAnnotation(Cell.class);
            CellProps cp = new CellProps();
            try {
                anno = anno == null ? PropsAnno.class.getDeclaredField("props").getAnnotation(Cell.class) : anno;
                cp.setHorizontal(anno.horizontal());
                cp.setVertical(anno.vertical());
                hps[i] = cp;
            } catch (NoSuchFieldException | SecurityException e) {
                // ignore.
            }
        }
        return hps;
    }
}
