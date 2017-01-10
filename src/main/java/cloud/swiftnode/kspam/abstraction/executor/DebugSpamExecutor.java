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
public class DebugSpamExecutor extends SpamExecutor {
    private SpamExecutor parent;

    public DebugSpamExecutor(SpamExecutor executor) {
        this.parent = executor;
    }

    @Override
    public boolean execute(SpamProcessor processor, SpamChecker checker, Deniable deniable) {
        boolean ret;
        long time = System.currentTimeMillis();
        ret = parent.execute(processor, checker, deniable);
        Static.consoleMsg(Lang.DEBUG.builder()
                .addKey(Lang.Key.PROCESSOR_NAME, Lang.Key.EXECUTOR_NAME, Lang.Key.CHECKER_NAME, Lang.Key.CHECKER_RESULT, Lang.Key.TIME)
                .addVal(processor.name(), this.name(), checker.name(), parent.getLastResult(), System.currentTimeMillis() - time));
        return ret;
    }
}
