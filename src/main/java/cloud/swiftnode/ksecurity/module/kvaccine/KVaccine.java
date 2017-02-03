package cloud.swiftnode.ksecurity.module.kvaccine;

import cloud.swiftnode.ksecurity.module.Module;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.gui.KGUI;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.processor.VirusScanProcessor;
import cloud.swiftnode.ksecurity.util.Config;
import cloud.swiftnode.ksecurity.util.Static;
import cloud.swiftnode.ksecurity.util.StaticStorage;
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
        // Load OpList
        StaticStorage.ALLOWED_OP_SET.addAll(Config.getOpList());
        // Start Application
        Static.runTaskAsync(KGUI::start);
    }

    @Override
    public String getSimpleVersion() {
        return "1.0";
    }
}
