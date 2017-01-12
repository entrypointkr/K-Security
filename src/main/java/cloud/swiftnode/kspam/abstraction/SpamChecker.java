package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.util.Lang;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class SpamChecker extends SimpleNamed implements Checker {
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

    public boolean isCaching() {
        return true;
    }

    public String denyMsg() {
        return Lang.DENY.toString();
    }

    public String getLastInfo() {
        return lastInfo;
    }

    public void setLastInfo(String lastInfo) {
        this.lastInfo = lastInfo;
    }

    public enum Result {
        PASS,
        DENY,
        ERROR,
        FORCE_PASS
    }
}
