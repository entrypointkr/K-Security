package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.util.Config;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.StaticStorage;
import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class FirstKickChecker extends SpamChecker {

    public FirstKickChecker(Info info) {
        super(info);
    }

    @Override
    public Result spamCheck() throws Exception {
        Player player = info.getPlayer();
        if (player == null || !Config.isFirstLoginKick() ||
                player.hasPlayedBefore()) {
            lastInfo = "";
            return Result.PASS;
        }
        String name = lastInfo = player.getName().toLowerCase();
        if (!StaticStorage.firstKickCachedSet.contains(name)) {
            StaticStorage.firstKickCachedSet.add(name);
            return Result.DENY;
        }
        StaticStorage.firstKickCachedSet.remove(name);
        return Result.PASS;
    }

    @Override
    public boolean isCaching() {
        return false;
    }

    @Override
    public String denyMsg() {
        return Lang.FIRST_LOGIN_KICK.toString();
    }
}
