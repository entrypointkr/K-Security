package cloud.swiftnode.kvaccine.abstraction.processor;

import cloud.swiftnode.kvaccine.abstraction.SpamExecutor;
import cloud.swiftnode.kvaccine.abstraction.SpamProcessor;
import cloud.swiftnode.kvaccine.abstraction.checker.CacheChecker;
import cloud.swiftnode.kvaccine.abstraction.checker.LocalChecker;
import cloud.swiftnode.kvaccine.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by Junhyeong Lim on 2017-01-18.
 */
public class SyncPingProcessor extends SpamProcessor {
    public SyncPingProcessor(SpamExecutor executor, DeniableInfoAdapter adapter) {
        super(executor, adapter);
        super.setCheckerList(LocalChecker.class, CacheChecker.class);
    }
}
