package cloud.swiftnode.ksecurity.abstraction.processor;

import cloud.swiftnode.ksecurity.abstraction.Processor;
import cloud.swiftnode.ksecurity.abstraction.manager.KPluginManager;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.network.KProxySelector;
import cloud.swiftnode.ksecurity.util.Reflections;
import cloud.swiftnode.ksecurity.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.net.ProxySelector;

/**
 * Created by Junhyeong Lim on 2017-01-25.
 */
public class InjectionProcessor implements Processor {
    @Override
    public boolean process() {
        Server server = Bukkit.getServer();
        try {
            Reflections.setDecalredFieldObject(server, "pluginManager",
                    new KPluginManager(Bukkit.getPluginManager()));

            ProxySelector.setDefault(new KProxySelector(ProxySelector.getDefault()));
        } catch (Exception ex) {
            Static.consoleMsg(ex);
        }
        return true;
    }
}
