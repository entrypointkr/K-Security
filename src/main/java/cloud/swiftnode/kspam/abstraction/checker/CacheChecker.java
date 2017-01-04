package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.util.StaticStorage;
import cloud.swiftnode.kspam.util.Tracer;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class CacheChecker extends SpamChecker {
    public CacheChecker(DeniableInfoAdapter adapter) {
        super(adapter);
    }

    @Override
    public Tracer.Result check() {
        String[] infos = new String[]{
                adapter.getIp(), adapter.getName(), adapter.getUUID().toString()
        };
        for (String info : infos) {
            if (StaticStorage.cachedSet.contains(info)) {
                return Tracer.Result.DENY;
            }
        }
        return Tracer.Result.PASS;
    }
}
