package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.storage.SpamStorage;

/**
 * Created by EntryPoint on 2016-12-20.
 */
public abstract class PunishProcesser extends RunnableProcesser {
    protected SpamStorage storage;

    public PunishProcesser(SpamStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        this.process();
    }
}
