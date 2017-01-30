package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.abstraction.Processor;
import cloud.swiftnode.kspam.abstraction.network.KProxySelector;
import cloud.swiftnode.kspam.abstraction.pluginmanager.KPluginManager;
import cloud.swiftnode.kspam.util.Reflections;
import cloud.swiftnode.kspam.util.Static;
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
