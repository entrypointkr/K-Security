package cloud.swiftnode.kspam.abstraction.executor;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class BaseSpamExecutor extends SpamExecutor {
    @Override
    public boolean execute(SpamProcessor processor, SpamChecker checker, Deniable deniable) {
        lastResult = checker.spamCheck();
        if (lastResult == SpamChecker.Result.FORCE_PASS) {
            return true;
        } else if (lastResult == SpamChecker.Result.DENY) {
            deniable.deny();
        } else if (lastResult == SpamChecker.Result.ERROR) {
            // TODO: Exception msg
        }
        return false;
    }
}
