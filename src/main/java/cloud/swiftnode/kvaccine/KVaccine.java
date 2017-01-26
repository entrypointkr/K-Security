package cloud.swiftnode.kvaccine;

import cloud.swiftnode.kvaccine.abstraction.processor.CacheInitProcessor;
import cloud.swiftnode.kvaccine.abstraction.processor.CacheSaveProcessor;
import cloud.swiftnode.kvaccine.abstraction.processor.InjectionProcessor;
import cloud.swiftnode.kvaccine.abstraction.processor.MetricsInitProcessor;
import cloud.swiftnode.kvaccine.abstraction.processor.UpdateCheckProcessor;
import cloud.swiftnode.kvaccine.abstraction.processor.VirusScanProcessor;
import cloud.swiftnode.kvaccine.command.Commands;
import cloud.swiftnode.kvaccine.listener.PlayerListener;
import cloud.swiftnode.kvaccine.listener.ServerListener;
import cloud.swiftnode.kvaccine.util.Config;
import cloud.swiftnode.kvaccine.util.Lang;
import cloud.swiftnode.kvaccine.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class KVaccine extends JavaPlugin {
    public static Plugin INSTANCE;

    @Override
    public void onLoad() {
        new InjectionProcessor().process();
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new ServerListener(), this);
        saveDefaultConfig();
        new CacheInitProcessor().process();
        new MetricsInitProcessor().process();
        Static.runTaskTimerAsync(() -> new UpdateCheckProcessor().process(),0, Config.updateCheckPeriod() * 3600 * 20);
        getCommand("kvc").setExecutor(new Commands());
        Static.runTaskLaterAsync(() -> new VirusScanProcessor().process(), 20);
        Static.consoleMsg(Lang.INTRO.builder()
                .single(Lang.Key.KSPAM_VERSION, Static.getVersion())
                .build(false));
    }

    @Override
    public void onDisable() {
        saveConfig();
        new CacheSaveProcessor().process();
    }
}
