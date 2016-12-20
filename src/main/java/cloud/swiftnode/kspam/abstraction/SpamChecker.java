package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.storage.SpamStorage;

/**
 * Created by EntryPoint on 2016-12-20.
 */
public abstract class SpamChecker implements Checker {
    protected SpamStorage storage;

    public SpamChecker(SpamStorage storage) {
        this.storage = storage;
    }
}
