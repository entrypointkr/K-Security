package cloud.swiftnode.kspam.util;

import cloud.swiftnode.kspam.abstraction.Checker;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class Tracer {
    private Result result;
    private Checker lastChecker;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Checker getLastChecker() {
        return lastChecker;
    }

    public void setLastChecker(Checker lastChecker) {
        this.lastChecker = lastChecker;
    }

    public enum Result {
        PASS,
        DENY,
        ERROR
    }
}
