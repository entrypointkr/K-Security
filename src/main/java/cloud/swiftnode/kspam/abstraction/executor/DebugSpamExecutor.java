package cloud.swiftnode.kspam.abstraction.executor;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class DebugSpamExecutor extends SpamExecutor {
    private SpamExecutor executor;

    public DebugSpamExecutor(SpamProcessor processor, SpamExecutor executor) {
        this.executor = executor;
    }

    @Override
    public boolean execute(SpamProcessor processor, SpamChecker checker, Deniable deniable) {
        boolean ret;
        long time = System.currentTimeMillis();
        ret = executor.execute(processor, checker, deniable);
        // TODO: System.currentTimeMills() - time;
        return ret;
    }
}
