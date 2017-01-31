package cloud.swiftnode.ksecurity.module.kspam.abstraction.checker;

import cloud.swiftnode.ksecurity.module.kspam.abstraction.Info;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamChecker;
import cloud.swiftnode.ksecurity.util.Static;
import cloud.swiftnode.ksecurity.util.URLs;

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
        URL url = URLs.KSPAM_API.toUrl(lastInfo = info.getIp());
        String text = Static.readAllText(url);
        if (text.contains("true")) {
            return Result.DENY;
        } else if (text.contains("false")) {
            return Result.PASS;
        }
        return Result.ERROR;
    }
}
