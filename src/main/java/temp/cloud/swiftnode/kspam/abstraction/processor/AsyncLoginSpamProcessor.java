package temp.cloud.swiftnode.kspam.abstraction.processor;

import temp.cloud.swiftnode.kspam.abstraction.SpamProcessor;
import temp.cloud.swiftnode.kspam.abstraction.SpamExecutor;
import temp.cloud.swiftnode.kspam.abstraction.checker.BotscoutChecker;
import temp.cloud.swiftnode.kspam.abstraction.checker.ShroomeryChecker;
import temp.cloud.swiftnode.kspam.abstraction.checker.StopforumChecker;
import temp.cloud.swiftnode.kspam.abstraction.checker.SwiftnodeChecker;
import temp.cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class AsyncLoginSpamProcessor extends SpamProcessor {
    public AsyncLoginSpamProcessor(DeniableInfoAdapter adapter, SpamExecutor executor) {
        super(adapter, executor);
        super.addChecker(SwiftnodeChecker.class,
                StopforumChecker.class,
                ShroomeryChecker.class,
                BotscoutChecker.class);
    }
}
