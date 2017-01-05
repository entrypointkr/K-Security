package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by EntryPoint on 2017-01-04.
 */
public abstract class SpamChecker implements Checker {
    protected DeniableInfoAdapter adapter;

    public SpamChecker(DeniableInfoAdapter adapter) {
        this.adapter = adapter;
    }
}
