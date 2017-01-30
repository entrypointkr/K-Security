package cloud.swiftnode.ksecurity.abstraction.checker;

import cloud.swiftnode.ksecurity.abstraction.Info;
import cloud.swiftnode.ksecurity.abstraction.SpamChecker;
import cloud.swiftnode.ksecurity.util.StaticStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class CacheChecker extends SpamChecker {
    public CacheChecker(Info info) {
        super(info);
    }

    @Override
    public Result spamCheck() {
        List<String> infoList = new ArrayList<>();
        try {
            infoList.add(info.getIp());
        } catch (IllegalStateException ex) {
            // Ignore
        }
        try {
            infoList.add(info.getName());
        } catch (IllegalStateException ex) {
            // Ignore
        }
        try {
            infoList.add(info.getUniqueId());
        } catch (IllegalStateException ex) {
            // Ignore
        }
        for (String info : infoList) {
            if (info == null) continue;
            if (StaticStorage.cachedSet.contains(lastInfo = info)) {
                return Result.DENY;
            }
        }
        return Result.PASS;
    }

    @Override
    public boolean isCaching() {
        return false;
    }
}
