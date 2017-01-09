package temp.cloud.swiftnode.kspam.abstraction.checker;

import temp.cloud.swiftnode.kspam.abstraction.SpamChecker;
import temp.cloud.swiftnode.kspam.abstraction.SpamProcessor;
import temp.cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import temp.cloud.swiftnode.kspam.util.Tracer;

/**
 * Created by EntryPoint on 2017-01-05.
 */
public class LocalChecker extends SpamChecker {
    public LocalChecker(DeniableInfoAdapter adapter, SpamProcessor processor) {
        super(adapter, processor);
    }

    @Override
    public Tracer.Result check() throws Exception {
        String ip = adapter.getIp();
        if (ip.contains("192.168.") || ip.contains("127.0.") ||
                adapter.getPlayer() != null && adapter.getPlayer().isOp()) {
            return Tracer.Result.FORCE_PASS;
        }
        return Tracer.Result.PASS;
    }
}
