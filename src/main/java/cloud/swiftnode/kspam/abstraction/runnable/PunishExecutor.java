package cloud.swiftnode.kspam.abstraction.runnable;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Tracer;
import org.bukkit.Bukkit;

/**
 * Created by Junhyeong Lim on 2017-01-08.
 */
public class PunishExecutor extends SpamExecutor {
    @Override
    public void execute(Tracer tracer, Deniable deniable) {
        if (tracer.getResult() == Tracer.Result.DENY) {
            deniable.deny();
            Bukkit.broadcastMessage(Lang.DENIED.builder()
                    .single(Lang.Key.CHECKER_NAME, tracer.getLastChecker().name())
                    .build());
        }
    }
}
