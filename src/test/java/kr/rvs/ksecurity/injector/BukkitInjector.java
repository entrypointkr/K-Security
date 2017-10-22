package kr.rvs.ksecurity.injector;

import kr.rvs.ksecurity.factory.MockFactory;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;

/**
 * Created by Junhyeong Lim on 2017-10-22.
 */
public class BukkitInjector {
    public static void injectServer() {
        try {
            Field field = Bukkit.class.getDeclaredField("server");
            field.setAccessible(true);
            field.set(null, MockFactory.createMockServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
