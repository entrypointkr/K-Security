package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.SpamChecker;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class MCBlacklistChecker extends SpamChecker {
    public MCBlacklistChecker(Info info) {
        super(info);
    }

    @Override
    public Result spamCheck() throws Exception {
        // TODO: Check
        if ((lastInfo = info.getUniqueId()).equals("나쁜놈")) {
            return Result.DENY;
        }
        return Result.PASS;
    }

    @Override
    public String denyMsg() {
        return "차단 메세지";
    }
}
