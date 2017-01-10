package cloud.swiftnode.kspam.abstraction.info;

import cloud.swiftnode.kspam.abstraction.convertor.StringToIpConverter;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class EventInfo extends AbstractInfo {
    private PlayerEvent event;

    public EventInfo(PlayerEvent event) {
        this.event = event;
    }

    @Override
    public String getName() {
        return event.getPlayer().getName();
    }

    @Override
    public String getIp() {
        if (event instanceof PlayerLoginEvent) {
            return new StringToIpConverter(
                    ((PlayerLoginEvent) event).getAddress().toString()).convert();
        } else {
            return new StringToIpConverter(
                    event.getPlayer().getAddress().getAddress().toString()).convert();
        }
    }

    @Override
    public Player getPlayer() {
        return event.getPlayer();
    }
}
