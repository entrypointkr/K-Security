package cloud.swiftnode.kspam.abstraction;

/**
 * Created by EntryPoint on 2016-12-20.
 */
public interface Checker extends Runnable {
    // TODO: StorageChecker
    boolean check();
}
