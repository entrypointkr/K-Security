package cloud.swiftnode.kvaccine.abstraction.processor;

import cloud.swiftnode.kvaccine.abstraction.Processor;
import cloud.swiftnode.kvaccine.abstraction.pluginmanager.KPluginManager;
import cloud.swiftnode.kvaccine.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.lang.reflect.Field;

/**
 * Created by Junhyeong Lim on 2017-01-25.
 */
public class InjectionProcessor implements Processor {
    @Override
    public boolean process() {
        Server server = Bukkit.getServer();
        try {
            Field pluginManagerField = server.getClass().getDeclaredField("pluginManager");
            pluginManagerField.setAccessible(true);
            pluginManagerField.set(server, new KPluginManager(Bukkit.getPluginManager()));
        } catch (Exception ex) {
            Static.consoleMsg(ex);
        }
        return true;
    }
}
