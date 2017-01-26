package cloud.swiftnode.kvaccine.abstraction.processor;

import cloud.swiftnode.kvaccine.abstraction.SpamExecutor;
import cloud.swiftnode.kvaccine.abstraction.SpamProcessor;
import cloud.swiftnode.kvaccine.abstraction.checker.BotscoutChecker;
import cloud.swiftnode.kvaccine.abstraction.checker.MCBlacklistChecker;
import cloud.swiftnode.kvaccine.abstraction.checker.ShroomeryChecker;
import cloud.swiftnode.kvaccine.abstraction.checker.StopforumChecker;
import cloud.swiftnode.kvaccine.abstraction.checker.SwiftnodeChecker;
import cloud.swiftnode.kvaccine.abstraction.deniable.DeniableInfoAdapter;

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
