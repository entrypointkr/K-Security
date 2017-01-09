package temp.cloud.swiftnode.kspam.abstraction.executor;

import temp.cloud.swiftnode.kspam.abstraction.SpamExecutor;
import temp.cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import temp.cloud.swiftnode.kspam.util.Lang;
import temp.cloud.swiftnode.kspam.util.Tracer;
import org.bukkit.Bukkit;

/**
 * Created by Junhyeong Lim on 2017-01-08.
 */
public class PunishExecutor extends SpamExecutor {
    @Override
    public void execute(Tracer tracer, DeniableInfoAdapter adapter, long startTime) {
        if (tracer.getResult() == Tracer.Result.DENY) {
            adapter.deny();
            Bukkit.broadcastMessage(Lang.DENIED.builder()
                    .addKey(Lang.Key.VICTIM, Lang.Key.CHECKER_NAME)
                    .addVal(adapter.getName() == null ? adapter.getIp() : adapter.getName(), tracer.getLastChecker().name())
                    .single(Lang.Key.CHECKER_NAME, tracer.getLastChecker().name())
                    .prefix()
                    .build());
        }
    }
}
