package cloud.swiftnode.ksecurity.abstraction.manager;

import cloud.swiftnode.ksecurity.module.Module;
import cloud.swiftnode.ksecurity.util.Static;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-01-31.
 */
public class ModuleManager {
    private List<Module> moduleList = new ArrayList<>();
    private JavaPlugin plugin;

    public ModuleManager setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
        return this;
    }

    @SafeVarargs
    public final ModuleManager addModule(Class<? extends Module>... moduleClasses) {
        for (Class<? extends Module> moduleClass : moduleClasses) {
            try {
                Module module = moduleClass.getConstructor(JavaPlugin.class).newInstance(plugin);
                moduleList.add(module);
            } catch (Exception e) {
                Static.consoleMsg(e);
            }
        }
        return this;
    }

    public void enableModules() {
        moduleList.forEach(((module -> {
            module.setEnabled(true);
            module.onEnable();
        })));
    }

    public void disableModules() {
        moduleList.forEach((module -> {
            module.setEnabled(false);
            module.onDisable();
        }));
    }

    public void loadModules() {
        moduleList.forEach(Module::onLoad);
    }

    public List<Module> getModules() {
        return moduleList;
    }
}
