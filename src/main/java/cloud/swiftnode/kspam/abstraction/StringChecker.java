package cloud.swiftnode.kspam.abstraction;

/**
 * Created by EntryPoint on 2016-12-23.
 */
public abstract class StringChecker extends RunnableChecker {
    protected String str;

    public StringChecker(String str) {
        this.str = str;
    }
}
