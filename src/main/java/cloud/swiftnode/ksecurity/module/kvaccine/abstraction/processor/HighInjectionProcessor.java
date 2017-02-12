package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.processor;

import cloud.swiftnode.ksecurity.abstraction.Processor;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KPluginManager;
import cloud.swiftnode.ksecurity.util.Reflections;
import cloud.swiftnode.ksecurity.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

import java.lang.reflect.Field;

/**
 * Created by Junhyeong Lim on 2017-02-07.
 */
public class HighInjectionProcessor implements Processor {
    @Override
    public boolean process() {
        try {
            PluginManager manager = Bukkit.getPluginManager();
            if (manager  instanceof KPluginManager
                    && manager.getClass() == KPluginManager.class) {
                return true;
            } else if (manager.getClass() != SimplePluginManager.class) {
                PluginManagerFacade facade = new PluginManagerFacade(manager);
                PluginManager newManager = new SimplePluginManager(Bukkit.getServer(), facade.getCommandMap());
                for (Field element : manager.getClass().getDeclaredFields()) {
                    element.setAccessible(true);
                    try {
                        Reflections.setDecField(SimplePluginManager.class, newManager, element.getName(), element.get(manager));
                    } catch (Exception ex) {
                        // Ignore
                    }
                }
                for (Field element : manager.getClass().getFields()) {
                    try {
                        Reflections.setField(SimplePluginManager.class, newManager, element.getName(), element.get(manager));
                    } catch (Exception ex) {
                        // Ignore
                    }
                }
                manager = newManager;
            }
            // PluginManager
            Reflections.setDecField(Bukkit.getServer(), "pluginManager",
                    new KPluginManager(manager));
        } catch (Exception ex) {
            Static.consoleMsg(ex);
            return false;
        }
        return true;
    }

    class PluginManagerFacade {
        private PluginManager target;

        public PluginManagerFacade(PluginManager target) {
            this.target = target;
        }

        public SimpleCommandMap getCommandMap() throws NoSuchFieldException, IllegalAccessException {
            Object manager;
            if (target instanceof KPluginManager) {
                manager = Reflections.getDecFieldObj(KPluginManager.class, target, "parent");
            } else {
                manager = target;
            }
            return (SimpleCommandMap) Reflections.getDecFieldObj(SimplePluginManager.class, manager, "commandMap");
        }
    }
}
