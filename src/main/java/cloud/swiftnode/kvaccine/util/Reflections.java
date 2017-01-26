package cloud.swiftnode.kvaccine.util;

import java.lang.reflect.Field;

/**
 * Created by Junhyeong Lim on 2017-01-24.
 */
public class Reflections {
    public static Object getFieldObject(Object obj, String fieldName, boolean pub) throws NoSuchFieldException, IllegalAccessException {
        Field field = null;
        if (pub) {
            field = obj.getClass().getField(fieldName);
        } else {
            field = obj.getClass().getDeclaredField(fieldName);
        }
        field.setAccessible(true);
        return field.get(obj);
    }

    public static Object getDeclaredFieldObject(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getFieldObject(obj, fieldName, false);
    }

    public static Object getFieldObject(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return getFieldObject(obj, fieldName, true);
    }
}
