package cloud.swiftnode.ksecurity.abstraction.processor;

import cloud.swiftnode.ksecurity.abstraction.SpamExecutor;
import cloud.swiftnode.ksecurity.abstraction.SpamProcessor;
import cloud.swiftnode.ksecurity.abstraction.checker.CacheChecker;
import cloud.swiftnode.ksecurity.abstraction.checker.LocalChecker;
import cloud.swiftnode.ksecurity.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by Junhyeong Lim on 2017-01-18.
 */
public class SyncPingProcessor extends SpamProcessor {
    public SyncPingProcessor(SpamExecutor executor, DeniableInfoAdapter adapter) {
        super(executor, adapter);
        super.setCheckerList(LocalChecker.class, CacheChecker.class);
    }
}
