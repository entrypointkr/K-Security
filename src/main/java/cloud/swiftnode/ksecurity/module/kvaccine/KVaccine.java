package cloud.swiftnode.ksecurity.module.kvaccine;

import cloud.swiftnode.ksecurity.module.Module;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.processor.HighInjectionProcessor;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.processor.LowInjectionProcessor;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.processor.VirusScanProcessor;
import cloud.swiftnode.ksecurity.util.Static;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Created by Junhyeong Lim on 2017-01-31.
 */
public class KVaccine extends Module {
    public KVaccine(JavaPlugin parent) {
        super(parent);
    }

    @Override
    public void onEnable() {
        // Virus Scan
        Static.runTaskAsync(() -> new VirusScanProcessor().process());
        // Injection
        Static.runTask(() -> new HighInjectionProcessor().process());
    }

    @Override
    public String getSimpleVersion() {
        return "1.0";
    }

    @Override
    public void onLoad() {
        new LowInjectionProcessor().process();
    }
}
