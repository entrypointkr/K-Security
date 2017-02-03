package cloud.swiftnode.ksecurity.util;

import java.lang.reflect.Field;

/**
 * Created by Junhyeong Lim on 2017-01-24.
 */
public class Reflections {
    private static Field getField(Class cls, String fieldName, boolean pub) throws NoSuchFieldException {
        if (pub) {
            return cls.getField(fieldName);
        }
        Field field = cls.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    public static Object getFieldObj(Class cls, Object obj, String fieldName, boolean pub) throws NoSuchFieldException, IllegalAccessException {
        return getField(cls, fieldName, pub).get(obj);
    }

    public static Object getFieldObj(Class cls, Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getFieldObj(cls, obj, fieldName, true);
    }

    public static Object getDecFieldObj(Class cls, Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getFieldObj(cls, obj, fieldName, false);
    }

    public static Object getDecFieldObj(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getDecFieldObj(obj.getClass(), obj, fieldName);
    }

    public static Object getFieldObj(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getFieldObj(obj.getClass(), obj, fieldName);
    }

    public static void setField(Class cls, Object inst, String fieldName, Object val, boolean pub) throws NoSuchFieldException, IllegalAccessException {
        getField(cls, fieldName, pub).set(inst, val);
    }

    public static void setField(Object inst, String fieldName, Object val, boolean pub) throws NoSuchFieldException, IllegalAccessException {
        setField(inst.getClass(), inst, fieldName, val, pub);
    }

    public static void setDecField(Class cls, Object inst, String fieldName, Object val) throws NoSuchFieldException, IllegalAccessException {
        setField(cls, inst, fieldName, val, false);
    }

    public static void setDecField(Object inst, String fieldName, Object val) throws NoSuchFieldException, IllegalAccessException {
        setField(inst.getClass(), inst, fieldName, val, false);
    }
}
