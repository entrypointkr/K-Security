package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Tracer;
import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-01-09.
 */
public class FirstKickChecker extends SpamChecker {
    public FirstKickChecker(DeniableInfoAdapter adapter) {
        super(adapter);
    }

    @Override
    public Tracer.Result check() throws Exception {
        Player player = adapter.getPlayer();
        if (player != null && !player.hasPlayedBefore()) {
            adapter.setKickMsg(Lang.FIRST_LOGIN_KICK.toString());
            return Tracer.Result.DENY;
        }
        return Tracer.Result.PASS;
    }

    @Override
    public String name() {
        return "FirstKickChecker";
    }
}
