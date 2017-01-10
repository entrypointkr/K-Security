package cloud.swiftnode.kspam.abstraction;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class SpamChecker implements Checker {
    protected Info info;
    protected String lastInfo;

    public SpamChecker(Info info) {
        this.info = info;
    }

    @Override
    public boolean check() {
        try {
            return spamCheck() == Result.DENY;
        } catch (Exception ex) {
            // Ignore
        }
        return false;
    }

    public abstract Result spamCheck() throws Exception;

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    public boolean isCaching() {
        return true;
    }

    public String getLastInfo() {
        return lastInfo;
    }

    public enum Result {
        PASS,
        DENY,
        ERROR,
        FORCE_PASS
    }
}
