package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.util.Tracer;

/**
 * Created by EntryPoint on 2017-01-05.
 */
public class LocalChecker extends SpamChecker {
    public LocalChecker(DeniableInfoAdapter adapter) {
        super(adapter);
    }

    @Override
    public Tracer.Result check() {
        String ip = adapter.getIp();
        if (ip.contains("192.168.") || ip.contains("127.0.")) {
            return Tracer.Result.FORCE_PASS;
        }
        return Tracer.Result.PASS;
    }

    @Override
    public String name() {
        return "LocalChecker";
    }
}
