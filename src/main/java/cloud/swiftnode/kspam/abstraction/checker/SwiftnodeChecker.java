package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.SpamChecker;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class SwiftnodeChecker extends SpamChecker {
    public SwiftnodeChecker(Info info) {
        super(info);
    }

    @Override
    public Result spamCheck() {
        return null;
    }
}
