package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.util.StaticStorage;
import cloud.swiftnode.kspam.util.Tracer;

/**
 * Created by Junhyeong Lim on 2017-01-08.
 */
public class ForceChecker extends SpamChecker {
    public ForceChecker(DeniableInfoAdapter adapter) {
        super(adapter);
    }

    @Override
    public Tracer.Result check() throws Exception {
        if (StaticStorage.forceMode &&
                adapter.getPlayer() != null && !adapter.getPlayer().hasPlayedBefore()) {
            adapter.getIp();
            adapter.caching();
            return Tracer.Result.DENY;
        }
        return Tracer.Result.PASS;
    }

    @Override
    public String name() {
        return "ForceChecker";
    }
}
