package cloud.swiftnode.kvaccine.abstraction.checker;


import cloud.swiftnode.kvaccine.abstraction.Info;
import cloud.swiftnode.kvaccine.abstraction.SpamChecker;
import cloud.swiftnode.kvaccine.util.Static;
import cloud.swiftnode.kvaccine.util.URLs;

import java.net.URL;

/**
 * Created by EntryPoint on 2017-01-05.
 */
public class StopforumChecker extends SpamChecker {
    public StopforumChecker(Info info) {
        super(info);
    }

    @Override
    public Result spamCheck() throws Exception {
        URL url = URLs.STOPFORUM_API.toUrl(lastInfo = info.getIp());
        String contents = Static.readAllText(url);
        if (contents.contains("<appears>")) {
            String appears = Static.substring(contents, "<appears>", "</appears>");
            if (appears.contains("yes")) {
                return SpamChecker.Result.DENY;
            } else if (appears.contains("no")) {
                return Result.PASS;
            }
        }
        return Result.ERROR;
    }

    @Override
    public boolean onlyFirst() {
        return true;
    }
}
