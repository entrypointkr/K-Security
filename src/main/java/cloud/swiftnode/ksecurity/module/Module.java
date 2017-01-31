package cloud.swiftnode.ksecurity.module;

import cloud.swiftnode.ksecurity.abstraction.Named;
import cloud.swiftnode.ksecurity.util.Version;
import org.bukkit.plugin.Plugin;

/**
 * Created by Junhyeong Lim on 2017-01-31.
 */
public abstract class Module implements Named {
    protected boolean enabled;
    protected Version version;
    protected Plugin parent;

    public Module(Plugin parent) {
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

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onLoad() {
    }

    public abstract String getSimpleVersion();
}
