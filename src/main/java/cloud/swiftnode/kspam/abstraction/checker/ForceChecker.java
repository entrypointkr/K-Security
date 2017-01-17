package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.StaticStorage;

/**
 * Created by Junhyeong Lim on 2017-01-18.
 */
public class ForceChecker extends SpamChecker {
    public ForceChecker(Info info) {
        super(info);
    }

    @Override
    public Result spamCheck() throws Exception {
        lastInfo = info.getIp();
        if (StaticStorage.forceMode) {
            return Result.DENY;
        }
        return Result.PASS;
    }

    @Override
    public String denyMsg() {
        return Lang.FORCEMODE_ON.builder().prefix().build();
    }
}
