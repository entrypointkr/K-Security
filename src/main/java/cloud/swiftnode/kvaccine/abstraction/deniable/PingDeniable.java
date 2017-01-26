package cloud.swiftnode.kvaccine.abstraction.deniable;

import cloud.swiftnode.kvaccine.abstraction.ExecuteDeniable;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by Junhyeong Lim on 2017-01-12.
 */
public class PingDeniable extends ExecuteDeniable {
    private ServerListPingEvent event;

    public PingDeniable(boolean delayed, ServerListPingEvent event) {
        super(delayed);
        this.event = event;
    }

    @Override
    public void executeDeny() {
        event.setMotd(getDenyMsg());
    }
}
