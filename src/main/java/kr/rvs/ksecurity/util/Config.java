package kr.rvs.ksecurity.util;

import kr.rvs.ksecurity.initializer.Initializer;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
public class Config {
    private final FileConfiguration config;

    public Config(FileConfiguration config) {
        this.config = config;
        config.options().copyDefaults(true);
    }

    public boolean isAllow(Initializer initializer) {
        String key = "initializer." + initializer.getClass().getSimpleName();
        config.addDefault(key, true);
        return config.getBoolean(key, true);
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
