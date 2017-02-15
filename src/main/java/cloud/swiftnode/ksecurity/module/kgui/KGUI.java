package cloud.swiftnode.ksecurity.module.kgui;

import cloud.swiftnode.ksecurity.module.Module;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-02-04.
 */
public class KGUI extends Module {

    public KGUI(JavaPlugin parent) {
        super(parent);
    }

    @Override
    public void onLoad() {
        new Thread(KFX::start, "K-GUI Thread").start();
    }

    @Override
    public String getSimpleVersion() {
        return "1.0";
    }
}
