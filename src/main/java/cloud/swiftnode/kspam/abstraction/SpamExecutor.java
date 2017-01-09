package cloud.swiftnode.kspam.abstraction;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class SpamExecutor implements Named {
    public abstract boolean execute(SpamChecker checker, Deniable deniable);

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
