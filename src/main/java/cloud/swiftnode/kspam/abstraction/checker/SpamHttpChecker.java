package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.storage.SpamStorage;

/**
 * Created by EntryPoint on 2016-12-22.
 */
public class SpamHttpChecker extends SpamChecker {
    public SpamHttpChecker(SpamStorage storage) {
        super(storage);
    }

    @Override
    public boolean check() {
        new SpamSwiftnodeChecker(storage).check();
        new SpamBotscoutChecker(storage).check();
        new SpamStopforumChecker(storage).check();
        new SpamShroomeryChecker(storage).check();
        return true;
    }
}
