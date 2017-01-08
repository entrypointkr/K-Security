package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.checker.CacheChecker;
import cloud.swiftnode.kspam.abstraction.checker.ForceChecker;
import cloud.swiftnode.kspam.abstraction.checker.LocalChecker;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class SynchronizeSpamProcessor extends SpamProcessor {
    public SynchronizeSpamProcessor(DeniableInfoAdapter adapter) {
        super(adapter);
        super.addChecker(LocalChecker.class, ForceChecker.class, CacheChecker.class);
    }

    @Override
    public String name() {
        return "SynchronizeProcessor";
    }
}
