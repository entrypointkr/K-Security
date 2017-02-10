package cloud.swiftnode.ksecurity.module.kanticheat;

import cloud.swiftnode.ksecurity.module.Module;
import cloud.swiftnode.ksecurity.module.kanticheat.listener.CheatListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-02-11.
 */
public class KAntiCheat extends Module {
    public KAntiCheat(JavaPlugin parent) {
        super(parent);
    }

    @Override
    public void onEnable() throws Exception {
        Bukkit.getPluginManager().registerEvents(new CheatListener(), parent);
    }

    @Override
    public String getSimpleVersion() {
        return "1.0";
    }
}
