package cloud.swiftnode.kspam.abstraction;

/**
 * Created by EntryPoint on 2016-12-21.
 */
public abstract class RunnableChecker implements Checker {
    @Override
    public void run() {
        this.check();
    }
}
