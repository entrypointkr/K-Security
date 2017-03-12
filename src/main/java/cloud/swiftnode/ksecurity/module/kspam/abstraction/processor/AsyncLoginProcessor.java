package cloud.swiftnode.ksecurity.module.kspam.abstraction.processor;

import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.checker.BotscoutChecker;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.checker.MCBlacklistChecker;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.checker.StopforumChecker;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.checker.SwiftnodeChecker;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class AsyncLoginProcessor extends SpamProcessor {
    public AsyncLoginProcessor(SpamExecutor executor, DeniableInfoAdapter adapter) {
        super(executor, adapter);
        super.setCheckerList(SwiftnodeChecker.class,
                MCBlacklistChecker.class,
                StopforumChecker.class,
                BotscoutChecker.class);
    }
}
