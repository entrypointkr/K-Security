package cloud.swiftnode.kspam.util;

import cloud.swiftnode.kspam.abstraction.Checkable;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class Tracer {
    private Result result;
    private Checkable lastChecker;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Checkable getLastChecker() {
        return lastChecker;
    }

    public void setLastChecker(Checkable lastChecker) {
        this.lastChecker = lastChecker;
    }

    public enum Result {
        PASS,
        DENY,
        ERROR
    }
}
