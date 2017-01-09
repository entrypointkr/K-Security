package temp.cloud.swiftnode.kspam.abstraction.checker;

import temp.cloud.swiftnode.kspam.abstraction.SpamChecker;
import temp.cloud.swiftnode.kspam.abstraction.SpamProcessor;
import temp.cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import temp.cloud.swiftnode.kspam.util.StaticStorage;
import temp.cloud.swiftnode.kspam.util.Tracer;

/**
 * Created by Junhyeong Lim on 2017-01-08.
 */
public class ForceChecker extends SpamChecker {
    public ForceChecker(DeniableInfoAdapter adapter, SpamProcessor processor) {
        super(adapter, processor);
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
}
