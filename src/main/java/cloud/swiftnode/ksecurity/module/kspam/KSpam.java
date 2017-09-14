package cloud.swiftnode.ksecurity.module.kspam;

import cloud.swiftnode.ksecurity.KSecurity;
import cloud.swiftnode.ksecurity.module.Module;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.processor.CacheInitProcessor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.processor.CacheSaveProcessor;
import cloud.swiftnode.ksecurity.util.StaticStorage;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-01-31.
 */
public class KSpam extends Module {
    public KSpam(JavaPlugin parent) {
        super(parent);
    }

    @Override
    public void onEnable() {
        new CacheInitProcessor().process();
        new FirstKickCachedMapGCThread().start();
    }

    @Override
    public void onDisable() {
        new CacheSaveProcessor().process();
    }

    @Override
    public String getSimpleVersion() {
        return "1.1";
    }
}
