package cloud.swiftnode.kspam.abstraction.executor;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.SpamExecutor;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class DebugSpamExecutor extends SpamExecutor {
    private SpamExecutor executor;

    public DebugSpamExecutor(SpamExecutor executor) {
        this.executor = executor;
    }

    @Override
    public boolean execute(SpamChecker checker, Deniable deniable) {
        boolean ret;
        long time = System.currentTimeMillis();
        ret = executor.execute(checker, deniable);
        // TODO: System.currentTimeMills() - time;
        return ret;
    }
}
