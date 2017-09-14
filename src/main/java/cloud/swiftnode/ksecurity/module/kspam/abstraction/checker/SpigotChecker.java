package cloud.swiftnode.ksecurity.module.kspam.abstraction.checker;

import cloud.swiftnode.ksecurity.module.kspam.abstraction.Info;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamChecker;

import java.net.InetAddress;

/**
 * Created by Junhyeong Lim on 2017-09-11.
 */
public class SpigotChecker extends SpamChecker {
    public SpigotChecker(Info info) {
        super(info);
    }

    @Override
    public Result spamCheck() {
        try {
            InetAddress address = InetAddress.getByName(info.getIp());
            String ip = lastInfo = address.getHostAddress();
            if (!address.isLoopbackAddress()) {
                String[] split = ip.split("\\.");
                StringBuilder lookup = new StringBuilder();
                for (int i = split.length - 1; i >= 0; --i) {
                    lookup.append(split[i]);
                    lookup.append(".");
                }
                lookup.append("xbl.spamhaus.org.");
                if (InetAddress.getByName(lookup.toString()) != null) {
                    return Result.DENY;
                }
            }
        } catch (Exception ignore) {

        }
        return Result.PASS;
    }
}
