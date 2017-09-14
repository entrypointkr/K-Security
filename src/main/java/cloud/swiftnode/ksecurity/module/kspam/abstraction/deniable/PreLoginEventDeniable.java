package cloud.swiftnode.ksecurity.module.kspam.abstraction.deniable;

import cloud.swiftnode.ksecurity.module.kspam.abstraction.ExecuteDeniable;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

/**
 * Created by Junhyeong Lim on 2017-09-15.
 */
public class PreLoginEventDeniable extends ExecuteDeniable {
    private final AsyncPlayerPreLoginEvent event;

    public PreLoginEventDeniable(boolean delayed, AsyncPlayerPreLoginEvent event) {
        super(delayed);
        this.event = event;
    }

    @Override
    public void executeDeny() {
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, getDenyMsg().build(false));
    }
}
