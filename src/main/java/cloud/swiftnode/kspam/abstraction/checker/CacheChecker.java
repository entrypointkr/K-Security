package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.util.StaticStorage;

import java.util.ArrayList;
import java.util.Arrays;
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
        List<String> infoList = new ArrayList<>(Arrays.asList(info.getIp(), info.getName()));
        try {
            infoList.add(info.getUniqueId());
        } catch (IllegalStateException ex) {
            // Ignore
        }
        for (String info : infoList) {
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
