package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.checker.FirstKickChecker;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class SyncJoinProcessor extends SpamProcessor {
    public SyncJoinProcessor(SpamExecutor executor, DeniableInfoAdapter adapter) {
        super(executor, adapter);
        setCheckerList(FirstKickChecker.class);
    }
}
