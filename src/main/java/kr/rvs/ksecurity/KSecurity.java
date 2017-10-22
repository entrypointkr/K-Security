package kr.rvs.ksecurity;

import kr.rvs.ksecurity.initializer.AntiBotInitializer;
import kr.rvs.ksecurity.initializer.BStatsInitializer;
import kr.rvs.ksecurity.initializer.Initializers;
import kr.rvs.ksecurity.initializer.MainInitializer;
import kr.rvs.ksecurity.util.Config;
import kr.rvs.ksecurity.util.Lang;
import kr.rvs.ksecurity.util.Static;
import kr.rvs.ksecurity.util.Updater;
import kr.rvs.ksecurity.util.Version;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.net.URI;

/**
 * Created by Junhyeong Lim on 2017-10-19.
 */
public class KSecurity extends JavaPlugin {
    @Override
    public void onEnable() {
        Static.init(this);
        Version.init(getDescription().getVersion());
        Updater.init(this);

        Static.log(Lang.INTRO.withoutPrefix());
        Config config = new Config(getConfig());
        new Initializers()
                .add(new MainInitializer(), new BStatsInitializer(), new AntiBotInitializer())
                .notify(this, config);
    }

    @Override
    public void onDisable() {
        saveConfig();
    }
}
