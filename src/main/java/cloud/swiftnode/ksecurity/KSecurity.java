package cloud.swiftnode.ksecurity;

import cloud.swiftnode.ksecurity.abstraction.manager.ModuleManager;
import cloud.swiftnode.ksecurity.module.kanticheat.KAntiCheat;
import cloud.swiftnode.ksecurity.module.kgui.KGUI;
import cloud.swiftnode.ksecurity.module.kparent.KParent;
import cloud.swiftnode.ksecurity.module.kspam.KSpam;
import cloud.swiftnode.ksecurity.module.kvaccine.KVaccine;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class KSecurity extends JavaPlugin {
    public static Plugin inst;
    private static final ModuleManager MODULE_MANAGER = new ModuleManager();

    public KSecurity() {
        init();
    }

    private void init() {
        inst = this;
        MODULE_MANAGER.setPlugin(this)
                .addModule(KParent.class, KSpam.class, KVaccine.class, KGUI.class, KAntiCheat.class);
    }

    @Override
    public void onLoad() {
        MODULE_MANAGER.loadModules();
    }

    @Override
    public void onEnable() {
        MODULE_MANAGER.enableModules();
    }

    @Override
    public void onDisable() {
        MODULE_MANAGER.disableModules();
    }

    public static ModuleManager getModuleManager() {
        return MODULE_MANAGER;
    }
}
