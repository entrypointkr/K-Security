package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.Tracer;
import cloud.swiftnode.kspam.util.URLs;

import java.net.URL;

/**
 * Created by EntryPoint on 2017-01-05.
 */
public class ShroomeryChecker extends SpamChecker {
    public ShroomeryChecker(DeniableInfoAdapter adapter) {
        super(adapter);
    }

    @Override
    public Tracer.Result check() throws Exception {
        URL url = URLs.SHROOMERY_API.toUrl(adapter.getIp());
        String contents = Static.readAllText(url);
        if (contents.contains("Y")) {
            adapter.caching();
            return Tracer.Result.DENY;
        } else if (contents.contains("N")) {
            return Tracer.Result.PASS;
        } else {
            return Tracer.Result.ERROR;
        }
    }

    @Override
    public String name() {
        return "ShroomeryChecker";
    }
}
