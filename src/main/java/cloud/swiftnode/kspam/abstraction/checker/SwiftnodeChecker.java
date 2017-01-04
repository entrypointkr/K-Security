package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.util.Tracer;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class SwiftnodeChecker extends SpamChecker {
    public SwiftnodeChecker(DeniableInfoAdapter deniable) {
        super(deniable);
    }

    @Override
    public Tracer.Result check() {
        return Tracer.Result.DENY;
    }
}
