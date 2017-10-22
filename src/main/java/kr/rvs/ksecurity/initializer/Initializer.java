package kr.rvs.ksecurity.initializer;

import kr.rvs.ksecurity.util.Config;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Junhyeong Lim on 2017-10-19.
 */
public interface Initializer {
    default Result check(JavaPlugin plugin, Config config) {
        return config.isAllow(this)
                ? Result.ofDefault()
                : new Result(false, "설정파일에 의해 비활성화 됨");
    }

    void init(JavaPlugin plugin);
}
