package cloud.swiftnode.ksecurity.abstraction.processor;

import cloud.swiftnode.ksecurity.abstraction.SpamExecutor;
import cloud.swiftnode.ksecurity.abstraction.SpamProcessor;
import cloud.swiftnode.ksecurity.abstraction.checker.BotscoutChecker;
import cloud.swiftnode.ksecurity.abstraction.checker.MCBlacklistChecker;
import cloud.swiftnode.ksecurity.abstraction.checker.ShroomeryChecker;
import cloud.swiftnode.ksecurity.abstraction.checker.StopforumChecker;
import cloud.swiftnode.ksecurity.abstraction.checker.SwiftnodeChecker;
import cloud.swiftnode.ksecurity.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class AsyncLoginProcessor extends SpamProcessor {
    public AsyncLoginProcessor(SpamExecutor executor, DeniableInfoAdapter adapter) {
        super(executor, adapter);
        super.setCheckerList(SwiftnodeChecker.class,
                MCBlacklistChecker.class,
                StopforumChecker.class,
                ShroomeryChecker.class,
                BotscoutChecker.class);
    }
}
