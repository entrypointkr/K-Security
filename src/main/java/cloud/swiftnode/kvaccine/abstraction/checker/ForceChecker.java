package cloud.swiftnode.kvaccine.abstraction.checker;

import cloud.swiftnode.kvaccine.abstraction.Info;
import cloud.swiftnode.kvaccine.abstraction.SpamChecker;
import cloud.swiftnode.kvaccine.util.Lang;
import cloud.swiftnode.kvaccine.util.StaticStorage;

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
        return Lang.FORCEMODE_ON.builder().build();
    }
}
