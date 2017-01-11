package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.checker.*;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;

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

