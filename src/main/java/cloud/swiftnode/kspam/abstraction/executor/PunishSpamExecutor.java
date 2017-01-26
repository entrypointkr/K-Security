package cloud.swiftnode.kspam.abstraction.executor;

import cloud.swiftnode.kspam.abstraction.DeniableInfo;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.util.StaticStorage;

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
