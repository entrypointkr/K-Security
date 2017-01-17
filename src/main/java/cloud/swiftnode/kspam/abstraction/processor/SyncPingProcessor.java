package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.checker.CacheChecker;
import cloud.swiftnode.kspam.abstraction.checker.LocalChecker;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by Junhyeong Lim on 2017-01-18.
 */
public class SyncPingProcessor extends SpamProcessor {
    public SyncPingProcessor(SpamExecutor executor, DeniableInfoAdapter adapter) {
        super(executor, adapter);
        super.setCheckerList(LocalChecker.class, CacheChecker.class);
    }
}
