package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import temp.cloud.swiftnode.kspam.util.Static;
import temp.cloud.swiftnode.kspam.util.URLs;

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
        String ip = info.getIp();
        URL url = URLs.COMMUNITY_API.toUrl(ip);
        String text = Static.readAllText(url);
        if (text.contains("true")) {
            return Result.DENY;
        } else if (text.contains("false")) {
            return Result.PASS;
        }
        return Result.ERROR;
    }
}
