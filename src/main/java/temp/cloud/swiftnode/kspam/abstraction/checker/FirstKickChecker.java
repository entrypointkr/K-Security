package temp.cloud.swiftnode.kspam.abstraction.checker;

import temp.cloud.swiftnode.kspam.KSpam;
import temp.cloud.swiftnode.kspam.abstraction.SpamChecker;
import temp.cloud.swiftnode.kspam.abstraction.SpamProcessor;
import temp.cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import temp.cloud.swiftnode.kspam.abstraction.executor.FirstKickExecutor;
import temp.cloud.swiftnode.kspam.util.Config;
import temp.cloud.swiftnode.kspam.util.Tracer;
import org.bukkit.entity.Player;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-01-09.
 */
public class FirstKickChecker extends SpamChecker {
    private static Set<String> nameSet = new LinkedHashSet<>();

    public FirstKickChecker(DeniableInfoAdapter adapter, SpamProcessor processor) {
        super(adapter, processor);
    }

    @Override
    public Tracer.Result check() throws Exception {
        Player player = adapter.getPlayer();
        if (player == null || !KSpam.INSTANCE.getConfig().getBoolean(Config.FIRST_LOGIN_KICK) || player.hasPlayedBefore()) {
            return Tracer.Result.PASS;
        }
        String name = player.getName().toLowerCase();
        if (!nameSet.contains(name)) {
            nameSet.add(name);
            if (!(processor.getExecutor() instanceof FirstKickExecutor)) {
                processor.setExecutor(new FirstKickExecutor());
            }
            return Tracer.Result.DENY;
        }
        nameSet.remove(name);
        return Tracer.Result.PASS;
    }
}
