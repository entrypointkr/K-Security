package cloud.swiftnode.kspam.abstraction;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class SpamExecutor extends SimpleNamed {
    protected SpamChecker.Result lastResult;

    public abstract boolean execute(SpamProcessor processor, SpamChecker checker, DeniableInfo adapter);

    public SpamChecker.Result getLastResult() {
        return lastResult;
    }
}
