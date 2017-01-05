package cloud.swiftnode.kspam.util;

import cloud.swiftnode.kspam.abstraction.Checker;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class Tracer {
    private Result result;
    private Checker lastChecker;
    private SpamProcessor lastProcessor;

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

    public SpamProcessor getLastProcessor() {
        return lastProcessor;
    }

    public void setLastProcessor(SpamProcessor lastProcessor) {
        this.lastProcessor = lastProcessor;
    }

    public enum Result {
        PASS,
        DENY,
        ERROR,
        FORCE_PASS
    }
}
