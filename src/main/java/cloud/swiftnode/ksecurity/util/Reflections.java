package cloud.swiftnode.ksecurity.util;

import java.lang.reflect.Field;

/**
 * Created by Junhyeong Lim on 2017-01-24.
 */
public class Reflections {
    public static Field getField(Object obj, String fieldName, boolean pub) throws NoSuchFieldException {
        if (pub) {
            return obj.getClass().getField(fieldName);
        }
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    public static Object getFieldObj(Object obj, String fieldName, boolean pub) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(obj, fieldName, pub);
        field.setAccessible(true);
        return field.get(obj);
    }

    public static Object getDecFieldObj(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getFieldObj(obj, fieldName, false);
    }

    public static Object getFieldObj(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getFieldObj(obj, fieldName, true);
    }

    public static void setField(Object inst, String fieldName, Object val, boolean pub) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(inst, fieldName, pub);
        field.setAccessible(true);
        field.set(inst, val);
    }

    public static void setDecField(Object inst, String fieldName, Object val) throws NoSuchFieldException, IllegalAccessException {
        setField(inst, fieldName, val, false);
    }
}
