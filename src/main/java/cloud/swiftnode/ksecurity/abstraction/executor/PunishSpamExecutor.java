package cloud.swiftnode.ksecurity.abstraction.executor;

import cloud.swiftnode.ksecurity.abstraction.DeniableInfo;
import cloud.swiftnode.ksecurity.abstraction.SpamChecker;
import cloud.swiftnode.ksecurity.abstraction.SpamExecutor;
import cloud.swiftnode.ksecurity.abstraction.SpamProcessor;
import cloud.swiftnode.ksecurity.util.StaticStorage;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class PunishSpamExecutor extends DecorateSpamExecutor {
    public PunishSpamExecutor(SpamExecutor parent) {
        super(parent);
    }

    public PunishSpamExecutor() {
        super(new BaseSpamExecutor());
    }

    @Override
    public boolean execute(SpamProcessor processor, SpamChecker checker, DeniableInfo adapter) {
        boolean ret = parent.execute(processor, checker, adapter);
        if (getLastResult() == SpamChecker.Result.DENY) {
            adapter.setDenyMsg(checker.denyMsg());
            adapter.deny();
            if (checker.isCaching()) {
                StaticStorage.cachedSet.add(checker.getLastInfo());
            }
        }
        return ret;
    }
}
