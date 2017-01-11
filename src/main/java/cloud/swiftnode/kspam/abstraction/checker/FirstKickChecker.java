package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.KSpam;
import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.util.Config;
import org.bukkit.entity.Player;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class FirstKickChecker extends SpamChecker {
    private Set<String> cachedSet = new LinkedHashSet<>();

    public FirstKickChecker(Info info) {
        super(info);
    }

    @Override
    public Result spamCheck() throws Exception {
        Player player = info.getPlayer();
        if (player == null || !KSpam.INSTANCE.getConfig().getBoolean(Config.FIRST_LOGIN_KICK) ||
                player.hasPlayedBefore()) {
            return Result.PASS;
        }
        String name = player.getName().toLowerCase();
        if (!cachedSet.contains(name)) {
            cachedSet.add(name);
            return Result.DENY;
        }
        cachedSet.remove(name);
        return Result.PASS;
    }

    @Override
    public boolean isCaching() {
        return false;
    }
}
