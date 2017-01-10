package cloud.swiftnode.kspam.abstraction.executor;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class BaseSpamExecutor extends SpamExecutor {
    @Override
    public boolean execute(SpamProcessor processor, SpamChecker checker, Deniable deniable) {
        try {
            lastResult = checker.spamCheck();
        } catch (Exception ex) {
            lastResult = SpamChecker.Result.ERROR;
            Static.consoleMsg(ex);
        }
        if (lastResult == SpamChecker.Result.FORCE_PASS) {
            return true;
        } else if (lastResult == SpamChecker.Result.DENY) {
            deniable.deny();
        } else if (lastResult == SpamChecker.Result.ERROR) {
            Static.consoleMsg(Lang.ERROR.builder().addKey());
        }
        return false;
    }
}
