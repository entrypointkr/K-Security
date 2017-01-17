package cloud.swiftnode.kspam.abstraction.executor;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.StaticStorage;
import org.bukkit.Bukkit;

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
    public boolean execute(SpamProcessor processor, SpamChecker checker, Deniable deniable) {
        boolean ret = parent.execute(processor, checker, deniable);
        if (getLastResult() == SpamChecker.Result.DENY) {
            deniable.setDenyMsg(checker.denyMsg());
            deniable.deny();
            Bukkit.broadcastMessage(Lang.DENIED.builder()
                    .addKey(Lang.Key.VICTIM, Lang.Key.CHECKER_NAME)
                    .addVal(((Info) deniable).getName(), checker.name())
                    .prefix().build());
            if (checker.isCaching()) {
                StaticStorage.cachedSet.add(checker.getLastInfo());
            }
        }
        return ret;
    }
}
