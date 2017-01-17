package cloud.swiftnode.kspam;

import cloud.swiftnode.kspam.abstraction.processor.CacheInitProcessor;
import cloud.swiftnode.kspam.abstraction.processor.CacheSaveProcessor;
import cloud.swiftnode.kspam.abstraction.processor.MetricsInitProcessor;
import cloud.swiftnode.kspam.abstraction.processor.ShutdownProcessor;
import cloud.swiftnode.kspam.abstraction.processor.UpdateCheckProcessor;
import cloud.swiftnode.kspam.command.Commands;
import cloud.swiftnode.kspam.listener.PlayerListener;
import cloud.swiftnode.kspam.listener.ServerListener;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class KSpam extends JavaPlugin {
    public static Plugin INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new ServerListener(), this);
        saveDefaultConfig();
        new CacheInitProcessor().process();
        new UpdateCheckProcessor().process();
        new MetricsInitProcessor().process();
        getCommand("kspam").setExecutor(new Commands());
        Static.consoleMsg(Lang.INTRO.builder()
                .single(Lang.Key.KSPAM_VERSION, Static.getVersion()));
    }

    @Override
    public void onDisable() {
        saveConfig();
        new CacheSaveProcessor().process();
        new ShutdownProcessor().process();
    }
}
