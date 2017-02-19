package cloud.swiftnode.ksecurity.util.injector;

import cloud.swiftnode.ksecurity.util.Reflections;
import cloud.swiftnode.ksecurity.util.Static;
import cloud.swiftnode.ksecurity.util.factory.InterceptorFactory;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

/**
 * Created by Junhyeong Lim on 2017-01-25.
 */
public class PluginManagerInjector {
    public static int process() {
        try {
            PluginManager manager = Bukkit.getPluginManager();
            PluginManager newManager = getManager();

            for (Field field : manager.getClass().getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    Object obj = field.get(manager);
                    Reflections.setDecField(newManager, field.getName(), obj);
                } catch (Exception ex) {
                    // Ignore
                }
            }

            Reflections.setDecField(Bukkit.getServer(), "pluginManager", newManager);
            return newManager.hashCode();
        } catch (Exception ex) {
            Static.consoleMsg(ex);
        }
        return -1;
    }

    private static PluginManager getManager() throws Exception {
        Constructor constructor = InterceptorFactory.createKPluginManager().getConstructor(Server.class, SimpleCommandMap.class);
        return (PluginManager) constructor.newInstance(null, null);
    }
}
