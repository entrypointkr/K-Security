package cloud.swiftnode.ksecurity;

import cloud.swiftnode.ksecurity.abstraction.manager.ModuleManager;
import cloud.swiftnode.ksecurity.abstraction.processor.InjectionProcessor;
import cloud.swiftnode.ksecurity.abstraction.processor.MetricsInitProcessor;
import cloud.swiftnode.ksecurity.abstraction.processor.UpdateCheckProcessor;
import cloud.swiftnode.ksecurity.command.Commands;
import cloud.swiftnode.ksecurity.listener.PlayerListener;
import cloud.swiftnode.ksecurity.listener.ServerListener;
import cloud.swiftnode.ksecurity.module.kspam.KSpam;
import cloud.swiftnode.ksecurity.module.kvaccine.KVaccine;
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
    private static ModuleManager moduleManager = new ModuleManager();

    @Override
    public void onLoad() {
        inst = this;
        Config.init();
        new InjectionProcessor().process();
        moduleManager.addModule(KSpam.class, KVaccine.class)
                .loadModules();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        moduleManager.enableModules();
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new ServerListener(), this);
        new MetricsInitProcessor().process();
        Static.runTaskTimerAsync(() -> new UpdateCheckProcessor().process(), 0, Config.updateCheckPeriod() * 3600 * 20);
        getCommand("ks").setExecutor(new Commands());
        Static.consoleMsg(Lang.INTRO.builder()
                .addKey(Lang.Key.KSPAM_VERSION, Lang.Key.MODULES_INFO)
                .addVal(Static.getVersion(), Lang.MODULES_INFO)
                .build(false, 1));
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    public static ModuleManager getModuleManager() {
        return moduleManager;
    }
}
