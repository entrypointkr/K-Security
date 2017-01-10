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
        SpamChecker.Result result = checker.spamCheck();
        if (result == SpamChecker.Result.FORCE_PASS) {
            return true;
        } else if (result == SpamChecker.Result.DENY) {
            deniable.deny();
        } else if (result == SpamChecker.Result.ERROR) {
            // TODO: Exception msg
        }
        return false;
    }
}
