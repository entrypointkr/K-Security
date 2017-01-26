package cloud.swiftnode.kvaccine.abstraction.checker;


import cloud.swiftnode.kvaccine.abstraction.Info;
import cloud.swiftnode.kvaccine.abstraction.SpamChecker;
import cloud.swiftnode.kvaccine.util.Static;
import cloud.swiftnode.kvaccine.util.URLs;

import java.net.URL;

/**
 * Created by EntryPoint on 2017-01-05.
 */
public class ShroomeryChecker extends SpamChecker {
    public ShroomeryChecker(Info info) {
        super(info);
    }

    @Override
    public Result spamCheck() throws Exception {
        URL url = URLs.SHROOMERY_API.toUrl(lastInfo = info.getIp());
        String contents = Static.readAllText(url);
        if (contents.contains("Y")) {
            return Result.DENY;
        } else if (contents.contains("N")) {
            return Result.PASS;
        } else {
            return Result.ERROR;
        }
    }

    @Override
    public boolean onlyFirst() {
        return true;
    }
}
