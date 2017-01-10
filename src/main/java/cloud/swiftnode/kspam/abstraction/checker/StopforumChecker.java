package cloud.swiftnode.kspam.abstraction.checker;


import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.URLs;

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
}
