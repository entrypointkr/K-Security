package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.checker.BotscoutChecker;
import cloud.swiftnode.kspam.abstraction.checker.ShroomeryChecker;
import cloud.swiftnode.kspam.abstraction.checker.StopforumChecker;
import cloud.swiftnode.kspam.abstraction.checker.SwiftnodeChecker;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class AsynchronousSpamProcessor extends SpamProcessor {
    public AsynchronousSpamProcessor(DeniableInfoAdapter adapter, SpamExecutor executor) {
        super(adapter, executor);
        super.addChecker(SwiftnodeChecker.class,
                StopforumChecker.class,
                ShroomeryChecker.class,
                BotscoutChecker.class);
    }

    @Override
    public String name() {
        return "AsynchronousProcessor";
    }
}
