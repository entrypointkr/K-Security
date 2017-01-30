package cloud.swiftnode.ksecurity.abstraction.processor;

import cloud.swiftnode.ksecurity.abstraction.SpamExecutor;
import cloud.swiftnode.ksecurity.abstraction.SpamProcessor;
import cloud.swiftnode.ksecurity.abstraction.checker.CacheChecker;
import cloud.swiftnode.ksecurity.abstraction.checker.FirstKickChecker;
import cloud.swiftnode.ksecurity.abstraction.checker.FirewallChecker;
import cloud.swiftnode.ksecurity.abstraction.checker.LocalChecker;
import cloud.swiftnode.ksecurity.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class SyncLoginProcessor extends SpamProcessor {
    public SyncLoginProcessor(SpamExecutor executor, DeniableInfoAdapter adapter) {
        super(executor, adapter);
        setCheckerList(LocalChecker.class, FirewallChecker.class, CacheChecker.class, FirstKickChecker.class);
    }
}
