package cloud.swiftnode.ksecurity.module.kspam.abstraction.executor;

import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamChecker;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamExecutor;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public abstract class DecorateSpamExecutor extends SpamExecutor {
    protected SpamExecutor parent;

    public DecorateSpamExecutor(SpamExecutor parent) {
        this.parent = parent;
    }

    @Override
    public String getName() {
        return super.getName() + "(" + parent.getName() + ")";
    }

    @Override
    public SpamChecker.Result getLastResult() {
        return parent.getLastResult();
    }
}
