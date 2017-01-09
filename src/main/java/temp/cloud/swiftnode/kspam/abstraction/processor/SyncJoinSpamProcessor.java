package temp.cloud.swiftnode.kspam.abstraction.processor;

import temp.cloud.swiftnode.kspam.abstraction.SpamExecutor;
import temp.cloud.swiftnode.kspam.abstraction.SpamProcessor;
import temp.cloud.swiftnode.kspam.abstraction.checker.FirstKickChecker;
import temp.cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class SyncJoinSpamProcessor extends SpamProcessor {
    public SyncJoinSpamProcessor(DeniableInfoAdapter adapter, SpamExecutor executor) {
        super(adapter, executor);
        addChecker(FirstKickChecker.class);
    }
}
