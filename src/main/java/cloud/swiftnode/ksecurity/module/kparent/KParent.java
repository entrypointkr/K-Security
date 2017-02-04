package cloud.swiftnode.ksecurity.module.kparent;

import cloud.swiftnode.ksecurity.command.Commands;
import cloud.swiftnode.ksecurity.listener.PlayerListener;
import cloud.swiftnode.ksecurity.listener.ServerListener;
import cloud.swiftnode.ksecurity.module.Module;
import cloud.swiftnode.ksecurity.module.kparent.abstraction.processor.InjectionProcessor;
import cloud.swiftnode.ksecurity.module.kparent.abstraction.processor.MetricsInitProcessor;
import cloud.swiftnode.ksecurity.module.kparent.abstraction.processor.UpdateCheckProcessor;
import cloud.swiftnode.ksecurity.util.Config;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-02-03.
 */
public class KParent extends Module {
    public KParent(JavaPlugin parent) {
        super(parent);
    }

    @Override
    public String getSimpleVersion() {
        return "1.0";
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), parent);
        Bukkit.getPluginManager().registerEvents(new ServerListener(), parent);
        new MetricsInitProcessor().process();
        Static.runTaskTimerAsync(() -> new UpdateCheckProcessor().process(), 0, Config.updateCheckPeriod() * 3600 * 20);
        getCommand("ks").setExecutor(new Commands());
        Static.consoleMsg(Lang.INTRO.builder()
                .addKey(Lang.Key.KSEC_VERSION, Lang.Key.MODULES_INFO)
                .addVal(Static.getVersion(), Lang.MODULES_INFO)
                .build(false, 1));
    }

    @Override
    public void onDisable() {
        parent.saveConfig();
    }

    @Override
    public void onLoad() {
        Config.init();
        new InjectionProcessor().process();
    }
}
