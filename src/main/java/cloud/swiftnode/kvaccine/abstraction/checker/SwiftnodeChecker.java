package cloud.swiftnode.kvaccine.abstraction.checker;

import cloud.swiftnode.kvaccine.abstraction.Info;
import cloud.swiftnode.kvaccine.abstraction.SpamChecker;
import cloud.swiftnode.kvaccine.util.Static;
import cloud.swiftnode.kvaccine.util.URLs;

import java.net.URL;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class SwiftnodeChecker extends SpamChecker {
    public SwiftnodeChecker(Info info) {
        super(info);
    }

    @Override
    public Result spamCheck() throws Exception {
        URL url = URLs.COMMUNITY_API.toUrl(lastInfo = info.getIp());
        String text = Static.readAllText(url);
        if (text.contains("true")) {
            return Result.DENY;
        } else if (text.contains("false")) {
            return Result.PASS;
        }
        return Result.ERROR;
    }
}
