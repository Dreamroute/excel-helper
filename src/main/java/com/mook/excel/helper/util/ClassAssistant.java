package com.mook.excel.helper.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ReflectPermission;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;

import com.mook.excel.helper.annotation.Sheet;

/**
 * util class
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

    public static List<Field> getAllFields(Class<?> dataCls) {
        List<Class<?>> superClsList = ClassUtils.getAllSuperclasses(dataCls);
        superClsList.add(dataCls); // add myself
        List<Field> fields = null;
        if (CollectionUtils.isNotEmpty(superClsList)) {
            fields = new ArrayList<>();
            for (Class<?> superCls : superClsList) {
                addFields(superCls, fields);
            }
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
                    // Ignored.
                }
            }
            if (field.isAccessible() && !fieldList.contains(field)) {
                int modifiers = field.getModifiers();
                if (!(Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers))) {
                    fieldList.add(field);
                }
            }
        }
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

}
