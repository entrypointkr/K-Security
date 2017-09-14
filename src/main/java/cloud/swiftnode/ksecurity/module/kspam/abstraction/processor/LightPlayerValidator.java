package cloud.swiftnode.ksecurity.module.kspam.abstraction.processor;

import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.checker.CacheChecker;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.checker.FirewallChecker;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.checker.FirstKickChecker;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.checker.LocalChecker;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.deniable.DeniableInfoAdapter;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class LightPlayerValidator extends SpamProcessor {
    public LightPlayerValidator(SpamExecutor executor, DeniableInfoAdapter adapter) {
        super(executor, adapter);
        setCheckerList(LocalChecker.class, FirewallChecker.class, CacheChecker.class);
    }
}
