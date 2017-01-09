package temp.cloud.swiftnode.kspam.abstraction;

import temp.cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by EntryPoint on 2017-01-04.
 */
public abstract class SpamChecker implements Checker {
    protected DeniableInfoAdapter adapter;
    protected SpamProcessor processor;

    public SpamChecker(DeniableInfoAdapter adapter, SpamProcessor processor) {
        this.adapter = adapter;
        this.processor = processor;
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
