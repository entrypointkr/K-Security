package temp.cloud.swiftnode.kspam.abstraction.processor;

import temp.cloud.swiftnode.kspam.abstraction.SpamProcessor;
import temp.cloud.swiftnode.kspam.abstraction.SpamExecutor;
import temp.cloud.swiftnode.kspam.abstraction.checker.CacheChecker;
import temp.cloud.swiftnode.kspam.abstraction.checker.ForceChecker;
import temp.cloud.swiftnode.kspam.abstraction.checker.LocalChecker;
import temp.cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class SyncLoginSpamProcessor extends SpamProcessor {
    public SyncLoginSpamProcessor(DeniableInfoAdapter adapter, SpamExecutor runnable) {
        super(adapter, runnable);
        super.addChecker(LocalChecker.class, ForceChecker.class, CacheChecker.class);
    }
}
