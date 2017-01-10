package cloud.swiftnode.kspam.abstraction.checker;


import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.StaticStorage;
import cloud.swiftnode.kspam.util.URLs;

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
        String ip = info.getIp();
        URL url = URLs.BOTSCOUT_API.toUrl(ip);
        String contents = Static.readAllText(url);
        if (contents.contains("Y|")) {
            return Result.DENY;
        } else if (contents.contains("N|")) {
            return Result.PASS;
        } else {
            return Result.ERROR;
        }
    }
}
