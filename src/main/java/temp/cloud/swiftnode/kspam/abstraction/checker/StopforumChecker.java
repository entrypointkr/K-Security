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
public class StopforumChecker extends SpamChecker {
    public StopforumChecker(DeniableInfoAdapter adapter, SpamProcessor processor) {
        super(adapter, processor);
    }

    @Override
    public Tracer.Result check() throws Exception {
        URL url = URLs.STOPFORUM_API.toUrl(adapter.getIp());
        String contents = Static.readAllText(url);
        if (contents.contains("<appears>")) {
            String appears = Static.substring(contents, "<appears>", "</appears>");
            if (appears.contains("yes")) {
                adapter.caching();
                return Tracer.Result.DENY;
            } else if (appears.contains("no")) {
                return Tracer.Result.PASS;
            }
        }
        return Tracer.Result.ERROR;
    }
}
