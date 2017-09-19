package cloud.swiftnode.ksecurity.module;

import cloud.swiftnode.ksecurity.abstraction.Named;
import cloud.swiftnode.ksecurity.util.Version;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-01-31.
 */
public abstract class Module implements Named {
    protected boolean enabled;
    protected Version version;
    protected JavaPlugin parent;

    public Module(JavaPlugin parent) {
        this.enabled = false;
        this.version = new Version(getSimpleVersion());
        this.parent = parent;
    }

    public Version getVersion() {
        return version;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void onEnable() throws Exception {
    }

    public void onDisable() throws Exception {
    }

    public void onLoad() throws Exception {
    }

    public abstract String getSimpleVersion();

    @Override
    public String getName() {
        return "&e" + getClass().getSimpleName();
    }

    public void saveDefaultConfig() {
        parent.saveDefaultConfig();
    }

    public void reloadConfig() {
        parent.reloadConfig();
    }

    public PluginCommand getCommand(String cmd) {
        return parent.getCommand(cmd);
    }

    public FileConfiguration getConfig() {
        return parent.getConfig();
    }

    public JavaPlugin getParent() {
        return parent;
    }
}
