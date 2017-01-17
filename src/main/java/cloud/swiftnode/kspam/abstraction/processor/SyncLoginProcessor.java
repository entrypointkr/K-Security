package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.checker.CacheChecker;
import cloud.swiftnode.kspam.abstraction.checker.ForceChecker;
import cloud.swiftnode.kspam.abstraction.checker.LocalChecker;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class SyncLoginProcessor extends SpamProcessor {
    public SyncLoginProcessor(SpamExecutor executor, DeniableInfoAdapter adapter) {
        super(executor, adapter);
        setCheckerList(LocalChecker.class, ForceChecker.class, CacheChecker.class);
    }
}
