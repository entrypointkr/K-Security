package cloud.swiftnode.ksecurity.module.kspam.abstraction.checker;


import cloud.swiftnode.ksecurity.module.kspam.abstraction.Info;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamChecker;
import cloud.swiftnode.ksecurity.util.Static;
import cloud.swiftnode.ksecurity.util.URLs;

import java.net.URL;

/**
 * Created by EntryPoint on 2017-01-05.
 */
public class BotscoutChecker extends SpamChecker {
    public BotscoutChecker(Info info) {
        super(info);
    }

    @Override
    public Result spamCheck() throws Exception {
        URL url = URLs.BOTSCOUT_API.toUrl(lastInfo = info.getIp());
        String contents = Static.readAllText(url).trim();
        if (contents.startsWith("Y|")) {
            return Result.DENY;
        } else if (contents.startsWith("N|")) {
            return Result.PASS;
        } else {
            return Result.ERROR;
        }
    }
}
