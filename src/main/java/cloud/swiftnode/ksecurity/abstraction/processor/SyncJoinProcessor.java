package cloud.swiftnode.ksecurity.abstraction.processor;

import cloud.swiftnode.ksecurity.abstraction.SpamExecutor;
import cloud.swiftnode.ksecurity.abstraction.SpamProcessor;
import cloud.swiftnode.ksecurity.abstraction.checker.FirstKickChecker;
import cloud.swiftnode.ksecurity.abstraction.checker.LocalChecker;
import cloud.swiftnode.ksecurity.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class SyncJoinProcessor extends SpamProcessor {
    public SyncJoinProcessor(SpamExecutor executor, DeniableInfoAdapter adapter) {
        super(executor, adapter);
        setCheckerList(LocalChecker.class, FirstKickChecker.class);
    }
}
