package temp.cloud.swiftnode.kspam.abstraction.checker;

import temp.cloud.swiftnode.kspam.abstraction.SpamChecker;
import temp.cloud.swiftnode.kspam.abstraction.SpamProcessor;
import temp.cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import temp.cloud.swiftnode.kspam.util.StaticStorage;
import temp.cloud.swiftnode.kspam.util.Tracer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class CacheChecker extends SpamChecker {
    public CacheChecker(DeniableInfoAdapter adapter, SpamProcessor processor) {
        super(adapter, processor);
    }

    @Override
    public Tracer.Result check() throws Exception {
        List<String> infoList = new ArrayList<>(Arrays.asList(adapter.getIp(), adapter.getName()));
        try {
            infoList.add(adapter.getUniqueId());
        } catch (IllegalStateException ex) {
            // Ignore
        }
        for (String info : infoList) {
            if (StaticStorage.cachedSet.contains(info)) {
                return Tracer.Result.DENY;
            }
        }
        return Tracer.Result.PASS;
    }
}
