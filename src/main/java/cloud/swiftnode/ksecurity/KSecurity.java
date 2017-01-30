package cloud.swiftnode.ksecurity;

import cloud.swiftnode.ksecurity.abstraction.processor.CacheInitProcessor;
import cloud.swiftnode.ksecurity.abstraction.processor.CacheSaveProcessor;
import cloud.swiftnode.ksecurity.abstraction.processor.InjectionProcessor;
import cloud.swiftnode.ksecurity.abstraction.processor.MetricsInitProcessor;
import cloud.swiftnode.ksecurity.abstraction.processor.UpdateCheckProcessor;
import cloud.swiftnode.ksecurity.abstraction.processor.VirusScanProcessor;
import cloud.swiftnode.ksecurity.command.Commands;
import cloud.swiftnode.ksecurity.listener.PlayerListener;
import cloud.swiftnode.ksecurity.listener.ServerListener;
import cloud.swiftnode.ksecurity.util.Config;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class KSecurity extends JavaPlugin {
    public static Plugin inst;

    @Override
    public void onLoad() {
        inst = this;
        Config.init();
        new InjectionProcessor().process();
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new ServerListener(), this);
        saveDefaultConfig();
        new CacheInitProcessor().process();
        new MetricsInitProcessor().process();
        Static.runTaskTimerAsync(() -> new UpdateCheckProcessor().process(),0, Config.updateCheckPeriod() * 3600 * 20);
        getCommand("ks").setExecutor(new Commands());
        Static.runTaskLaterAsync(() -> new VirusScanProcessor().process(), 20);
        Static.consoleMsg(Lang.INTRO.builder()
                .single(Lang.Key.KSPAM_VERSION, Static.getVersion()).build(false));
    }

    @Override
    public void onDisable() {
        saveConfig();
        new CacheSaveProcessor().process();
    }
}
