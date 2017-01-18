package cloud.swiftnode.kspam.abstraction.executor;

import cloud.swiftnode.kspam.abstraction.DeniableInfo;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.util.Config;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class BaseSpamExecutor extends SpamExecutor {
    @Override
    public boolean execute(SpamProcessor processor, SpamChecker checker, DeniableInfo adapter) {
        lastResult = SpamChecker.Result.ERROR;
        checker.setLastInfo("NONE");
        if (checker.onlyFirst() && adapter.getPlayer() != null
                && adapter.getPlayer().hasPlayedBefore()) {
            lastResult = SpamChecker.Result.PASS;
            checker.setLastInfo("OnlyFirstPlayer");
            return false;
        }
        boolean alert = Config.isAlert();
        try {
            lastResult = checker.spamCheck();
        } catch (Exception ex) {
            Static.consoleMsg(ex);
        }
        if (lastResult == SpamChecker.Result.FORCE_PASS
                || lastResult == SpamChecker.Result.DENY) {
            return true;
        } else if (lastResult == SpamChecker.Result.ERROR) {
            if (alert) {
                Static.consoleMsg(Lang.ERROR.builder()
                        .single(Lang.Key.CHECKER_NAME, checker.name()).prefix());
            }
        }
        return false;
    }
}
