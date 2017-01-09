package cloud.swiftnode.kspam.abstraction.deniable;

import cloud.swiftnode.kspam.abstraction.Deniable;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class LoginEventDeniable implements Deniable {
    private PlayerLoginEvent event;

    public LoginEventDeniable(PlayerLoginEvent event) {
        this.event = event;
    }

    @Override
    public void deny() {
        event.disallow(PlayerLoginEvent.Result.KICK_BANNED, "K-SPAM");
    }
}
