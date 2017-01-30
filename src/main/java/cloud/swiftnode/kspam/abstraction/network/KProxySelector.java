package cloud.swiftnode.kspam.abstraction.network;

import cloud.swiftnode.kspam.KSpam;
import cloud.swiftnode.kspam.util.Config;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginLoader;

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
            Static.consoleMsg(Lang.TRY_NETWORKING.builder()
                    .addKey(Lang.Key.PLUGIN_NAME, Lang.Key.VALUE)
                    .addVal(Static.getRequestPlugin(new Exception()).getName(), uri));
        }
        return parent.select(uri);
    }

    private boolean isNetworkAlert() {
        if (KSpam.inst.isEnabled()) {
            return Config.isNetworkAlert();
        }
        return true;
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        System.out.println("failejfaiwojifewojifweajoiwfaejoiweaojifwaejoiwaejoifwaejoi");
        parent.connectFailed(uri, sa, ioe);
    }
}
