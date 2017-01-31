package cloud.swiftnode.ksecurity.module.kspam.abstraction.info;

import cloud.swiftnode.ksecurity.abstraction.convertor.StringToIpConverter;
import org.bukkit.entity.Player;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by Junhyeong Lim on 2017-01-12.
 */
public class PingInfo extends AbstractInfo {
    private ServerListPingEvent event;

    public PingInfo(ServerListPingEvent event) {
        this.event = event;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getIp() {
        return new StringToIpConverter(event.getAddress().toString()).convert();
    }

    @Override
    public Player getPlayer() {
        return null;
    }
}
