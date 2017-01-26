package cloud.swiftnode.kvaccine.abstraction.executor;

import cloud.swiftnode.kvaccine.abstraction.SpamChecker;
import cloud.swiftnode.kvaccine.abstraction.SpamExecutor;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public abstract class DecorateSpamExecutor extends SpamExecutor {
    protected SpamExecutor parent;

    public DecorateSpamExecutor(SpamExecutor parent) {
        this.parent = parent;
    }

    @Override
    public String name() {
        return super.name() + "(" + parent.name() + ")";
    }

    @Override
    public SpamChecker.Result getLastResult() {
        return parent.getLastResult();
    }
}
