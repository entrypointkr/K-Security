package cloud.swiftnode.ksecurity.module.kspam.abstraction.checker;

import cloud.swiftnode.ksecurity.module.kspam.abstraction.Info;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamChecker;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.StaticStorage;

/**
 * Created by Junhyeong Lim on 2017-01-18.
 */
public class FirewallChecker extends SpamChecker {
    public FirewallChecker(Info info) {
        super(info);
    }

    @Override
    public Result spamCheck() throws Exception {
        lastInfo = info.getIp();
        if (StaticStorage.firewallMode) {
            return Result.DENY;
        }
        return Result.PASS;
    }

    @Override
    public boolean isCaching() {
        return false;
    }

    @Override
    public Lang.MessageBuilder denyMsg() {
        return Lang.FORCEMODE_ON.builder();
    }
}
