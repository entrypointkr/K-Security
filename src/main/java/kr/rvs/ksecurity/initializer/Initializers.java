package kr.rvs.ksecurity.initializer;

import kr.rvs.ksecurity.util.Config;
import kr.rvs.ksecurity.util.Static;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-10-19.
 */
public class Initializers {
    private final List<Initializer> initializers = new ArrayList<>();

    public Initializers add(Initializer... initializers) {
        this.initializers.addAll(Arrays.asList(initializers));
        return this;
    }

    public void notify(JavaPlugin instance, Config config) {
        Static.log(ChatColor.YELLOW + "플러그인 초기화");
        for (Initializer initializer : initializers) {
            String initializerName = initializer.getClass().getSimpleName();
            Result result;
            try {
                result = initializer.check(instance, config);
                if (result.isSuccess())
                    initializer.init(instance);
            } catch (Exception ex) {
                Static.log(ex);
                result = new Result(false, "초기화 실패");
            }
            Static.log("... " + result.toMessage(initializerName));
        }
        Static.log(ChatColor.YELLOW + "완료");
    }
}
