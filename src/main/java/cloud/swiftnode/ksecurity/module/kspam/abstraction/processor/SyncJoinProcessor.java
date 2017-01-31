package cloud.swiftnode.ksecurity.module.kspam.abstraction.processor;

import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.checker.FirstKickChecker;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.checker.LocalChecker;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class SyncJoinProcessor extends SpamProcessor {
    public SyncJoinProcessor(SpamExecutor executor, DeniableInfoAdapter adapter) {
        super(executor, adapter);
        setCheckerList(LocalChecker.class, FirstKickChecker.class);
    }
}
