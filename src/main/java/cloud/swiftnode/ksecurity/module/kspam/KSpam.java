package cloud.swiftnode.ksecurity.module.kspam;

import cloud.swiftnode.ksecurity.module.Module;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.processor.CacheInitProcessor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.processor.CacheSaveProcessor;
import org.bukkit.plugin.Plugin;

/**
 * Created by Junhyeong Lim on 2017-01-31.
 */
public class KSpam extends Module {
    public KSpam(Plugin parent) {
        super(parent);
    }

    @Override
    public void onEnable() {
        new CacheInitProcessor().process();
    }

    @Override
    public void onDisable() {
        new CacheSaveProcessor().process();
    }

    @Override
    public String getSimpleVersion() {
        return "1.0";
    }

    @Override
    public String getName() {
        return "&eKSpam";
    }
}
