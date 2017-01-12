package cloud.swiftnode.kspam.abstraction.deniable;

import cloud.swiftnode.kspam.abstraction.ExecuteDeniable;
import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class PlayerDeniable extends ExecuteDeniable {
    private Player player;

    public PlayerDeniable(Player player) {
        super(true);
        this.player = player;
    }

    @Override
    public void executeDeny() {
        player.kickPlayer(getDenyMsg());
    }
}
