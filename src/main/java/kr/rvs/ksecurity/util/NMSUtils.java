package kr.rvs.ksecurity.util;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Junhyeong Lim on 2017-09-06.
 */
public class NMSUtils {
    private static String packageVersion;
    private static int compressionThreshold = -1;

    public static int getNextEntityCount() {
        int count = 0;
        try {
            Field countField = getNMSClass("Entity").getDeclaredField("entityCount");
            countField.setAccessible(true);
            count = (int) countField.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int getCompressionThreshold() {
        if (compressionThreshold == -1) {
            Server server = Bukkit.getServer();
            try {
                Method getServerMethod = server.getClass().getMethod("getServer");
                Object internalServer = getServerMethod.invoke(server);
                Method valueMethod = internalServer.getClass().getMethod("aG");
                compressionThreshold = (int) valueMethod.invoke(internalServer);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException(e);
            }
        }

        return compressionThreshold;
    }

    public static void setCompressionThreshold(Object networkManager, int threshold) {
        try {
            Method setMethod = networkManager.getClass().getMethod("setCompressionLevel", int.class);
            setMethod.invoke(networkManager, threshold);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String getPackageVersion() {
        if (packageVersion == null) {
            String packageName = Bukkit.getServer().getClass().getName();
            int point = packageName.indexOf(".v") + 1;

            packageVersion = packageName.substring(
                    point, packageName.indexOf('.', point));
        }
        return packageVersion;
    }

    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getPackageVersion() + '.' + name);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("NMS 클래스를 찾는 중 에러가 발생했습니다, " + name, e);
        }
    }
}
