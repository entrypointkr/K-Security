package cloud.swiftnode.ksecurity.module.kspam.abstraction;

import cloud.swiftnode.ksecurity.abstraction.Named;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class SpamExecutor implements Named {
    protected SpamChecker.Result lastResult;

    public abstract boolean execute(SpamProcessor processor, SpamChecker checker, DeniableInfo adapter);

    public SpamChecker.Result getLastResult() {
        return lastResult;
    }
}
