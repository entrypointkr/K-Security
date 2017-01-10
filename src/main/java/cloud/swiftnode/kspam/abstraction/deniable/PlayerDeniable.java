package cloud.swiftnode.kspam.abstraction.deniable;

import cloud.swiftnode.kspam.abstraction.ExecuteDeniable;
import cloud.swiftnode.kspam.util.Lang;
import org.bukkit.entity.Player;
import temp.cloud.swiftnode.kspam.util.Static;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class PlayerDeniable extends ExecuteDeniable {
    private Player player;

    public PlayerDeniable(boolean async, Player player) {
        super(async);
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
