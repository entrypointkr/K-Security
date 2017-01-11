package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.SpamChecker;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class LocalChecker extends SpamChecker {
    public LocalChecker(Info info) {
        super(info);
    }

    @Override
    public Result spamCheck() throws Exception {
        String ip = lastInfo = info.getIp();
        if (ip.contains("192.168.") || ip.contains("127.0.")
                || (info.getPlayer() != null && info.getPlayer().isOp())) {
            return Result.FORCE_PASS;
        }
        return Result.PASS;
    }
}
