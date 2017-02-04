package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter;

import cloud.swiftnode.ksecurity.KSecurity;
import cloud.swiftnode.ksecurity.util.Config;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Static;
import cloud.swiftnode.ksecurity.util.StaticStorage;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-01-31.
 */
public class KProxySelector extends ProxySelector {
    private ProxySelector parent;

    public KProxySelector(ProxySelector parent) {
        this.parent = parent;
    }

    @Override
    public List<Proxy> select(URI uri) {
        if (Config.isNetworkAlert()) {
            Plugin plugin = Static.getRequestPlugin();
            if (!StaticStorage.NET_ESCAPE_SET.contains(plugin.getName())) {
                Static.consoleMsg(Lang.TRY_NETWORKING.builder()
                        .addKey(Lang.Key.PLUGIN_NAME, Lang.Key.VALUE)
                        .addVal(Static.getRequestPlugin().getName(), uri));
            }
        }
        return parent.select(uri);
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        parent.connectFailed(uri, sa, ioe);
    }
}
