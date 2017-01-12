package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.StaticStorage;

/**
 * Created by Junhyeong Lim on 2017-01-12.
 */
public class PacketChecker extends SpamChecker {
    public PacketChecker(Info info) {
        super(info);
    }

    @Override
    public Result spamCheck() throws Exception {
        String name = info.getName().toLowerCase();
        if (StaticStorage.protocolLib && !StaticStorage.validateSet.contains(name)) {
            return Result.DENY;
        }
        StaticStorage.validateSet.remove(name);
        return Result.PASS;
    }

    @Override
    public boolean isCaching() {
        return false;
    }

    @Override
    public String denyMsg() {
        return Lang.ILLEGAL_ACCESS.toString();
    }
}
