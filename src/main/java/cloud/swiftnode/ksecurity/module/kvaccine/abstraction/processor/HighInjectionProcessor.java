package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.processor;

import cloud.swiftnode.ksecurity.abstraction.Processor;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KPluginManager;
import cloud.swiftnode.ksecurity.util.Reflections;
import cloud.swiftnode.ksecurity.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

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
            }
            // PluginManager
            Reflections.setDecField(Bukkit.getServer(), "pluginManager",
                    new KPluginManager(Bukkit.getPluginManager()));
        } catch (Exception ex) {
            Static.consoleMsg(ex);
            return false;
        }
        return true;
    }
}
