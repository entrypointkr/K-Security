package cloud.swiftnode.kspam;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class KSpam extends JavaPlugin {
    public static KSpam INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
    }
}
