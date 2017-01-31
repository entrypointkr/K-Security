package cloud.swiftnode.ksecurity.module.kvaccine;

import cloud.swiftnode.ksecurity.module.Module;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.processor.VirusScanProcessor;
import cloud.swiftnode.ksecurity.util.Static;
import org.bukkit.plugin.Plugin;

/**
 * Created by Junhyeong Lim on 2017-01-31.
 */
public class KVaccine extends Module {
    public KVaccine(Plugin parent) {
        super(parent);
    }

    @Override
    public void onEnable() {
        Static.runTaskLaterAsync(() -> new VirusScanProcessor().process(), 20);
    }

    @Override
    public String getSimpleVersion() {
        return "1.0";
    }

    @Override
    public String getName() {
        return "&eKVaccine";
    }
}
