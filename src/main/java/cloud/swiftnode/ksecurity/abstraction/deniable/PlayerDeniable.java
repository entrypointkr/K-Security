package cloud.swiftnode.ksecurity.abstraction.deniable;

import cloud.swiftnode.ksecurity.abstraction.ExecuteDeniable;
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
