package cloud.swiftnode.kvaccine.abstraction.checker;

import cloud.swiftnode.kvaccine.abstraction.Info;
import cloud.swiftnode.kvaccine.abstraction.SpamChecker;
import org.bukkit.entity.Player;

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
        Player player = info.getPlayer();
        if (ip.contains("192.168.") || ip.contains("127.0.")
                || player != null && player.isOp()) {
            return Result.FORCE_PASS;
        }
        return Result.PASS;
    }
}
