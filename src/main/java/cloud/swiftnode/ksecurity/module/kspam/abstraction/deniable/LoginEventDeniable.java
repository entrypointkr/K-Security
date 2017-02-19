package cloud.swiftnode.ksecurity.module.kspam.abstraction.deniable;

import cloud.swiftnode.ksecurity.module.kspam.abstraction.ExecuteDeniable;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class LoginEventDeniable extends ExecuteDeniable {
    private PlayerLoginEvent event;

    public LoginEventDeniable(boolean delayed, PlayerLoginEvent event) {
        super(delayed);
        this.event = event;
    }

    @Override
    public void executeDeny() {
        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, getDenyMsg().build(false));
    }
}
