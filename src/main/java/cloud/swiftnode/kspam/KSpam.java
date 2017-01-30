package cloud.swiftnode.kspam;

import cloud.swiftnode.kspam.abstraction.processor.CacheInitProcessor;
import cloud.swiftnode.kspam.abstraction.processor.CacheSaveProcessor;
import cloud.swiftnode.kspam.abstraction.processor.InjectionProcessor;
import cloud.swiftnode.kspam.abstraction.processor.MetricsInitProcessor;
import cloud.swiftnode.kspam.abstraction.processor.UpdateCheckProcessor;
import cloud.swiftnode.kspam.abstraction.processor.VirusScanProcessor;
import cloud.swiftnode.kspam.command.Commands;
import cloud.swiftnode.kspam.listener.PlayerListener;
import cloud.swiftnode.kspam.listener.ServerListener;
import cloud.swiftnode.kspam.util.Config;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class KSpam extends JavaPlugin {
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
        getCommand("kspam").setExecutor(new Commands());
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
