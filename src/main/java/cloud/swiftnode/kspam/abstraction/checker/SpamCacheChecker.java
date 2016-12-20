package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.storage.SpamStorage;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.util.Result;

/**
 * Created by EntryPoint on 2016-12-20.
 */
public class SpamCacheChecker extends SpamChecker {
    public SpamCacheChecker(SpamStorage storage) {
        super(storage);
    }

    @Override
    public boolean check() {
        String ip = super.storage.getIp();
        if (StaticStorage.getCachedIpSet().contains(ip)) {
            storage.setResult(Result.TRUE);
            return true;
        }
        return false;
    }
}
