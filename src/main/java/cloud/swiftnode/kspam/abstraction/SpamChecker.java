package cloud.swiftnode.kspam.abstraction;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class SpamChecker implements Checker {
    protected    Info info;

    public SpamChecker(Info info) {
        this.info = info;
    }

    @Override
    public boolean check() {
        return spamCheck() == Result.DENY;
    }

    public abstract Result spamCheck();

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    public enum Result {
        PASS,
        DENY,
        ERROR,
        FORCE_PASS
    }
}
