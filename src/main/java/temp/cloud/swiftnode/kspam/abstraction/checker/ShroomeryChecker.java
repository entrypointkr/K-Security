package temp.cloud.swiftnode.kspam.abstraction.checker;

import temp.cloud.swiftnode.kspam.abstraction.SpamChecker;
import temp.cloud.swiftnode.kspam.abstraction.SpamProcessor;
import temp.cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import temp.cloud.swiftnode.kspam.util.Static;
import temp.cloud.swiftnode.kspam.util.Tracer;
import temp.cloud.swiftnode.kspam.util.URLs;

import java.net.URL;

/**
 * Created by EntryPoint on 2017-01-05.
 */
public class ShroomeryChecker extends SpamChecker {
    public ShroomeryChecker(DeniableInfoAdapter adapter, SpamProcessor processor) {
        super(adapter, processor);
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
}
