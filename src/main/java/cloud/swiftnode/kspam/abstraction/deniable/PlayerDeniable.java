package cloud.swiftnode.kspam.abstraction.deniable;

import cloud.swiftnode.kspam.abstraction.ExecuteDeniable;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class PlayerDeniable extends ExecuteDeniable {
    private Player player;

    public PlayerDeniable(Mode mode, Player player) {
        super(mode);
        this.player = player;
    }

    @Override
    public void executeDeny() {
        Static.runTask(new Runnable() {
            @Override
            public void run() {
                player.kickPlayer(Lang.DENY.toString());
            }
        });
    }
}
