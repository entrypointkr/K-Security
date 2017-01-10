package cloud.swiftnode.kspam.abstraction.deniable;

import cloud.swiftnode.kspam.abstraction.ExecuteDeniable;
import cloud.swiftnode.kspam.util.Lang;
import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class PlayerDeniable extends ExecuteDeniable {
    private Player player;

    public PlayerDeniable(Player player) {
        super(Mode.SYNC);
        this.player = player;
    }

    @Override
    public void executeDeny() {
        player.kickPlayer(Lang.DENY.toString());
    }
}
