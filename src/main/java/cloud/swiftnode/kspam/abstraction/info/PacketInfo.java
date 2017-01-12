package cloud.swiftnode.kspam.abstraction.info;

import cloud.swiftnode.kspam.abstraction.convertor.StringToIpConverter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-01-12.
 */
public class PacketInfo extends AbstractInfo {
    private PacketEvent event;

    public PacketInfo(PacketEvent event) {
        this.event = event;
    }

    @Override
    public String getName() {
        return getPlayer().getName();
    }

    @Override
    public String getIp() {
        return new StringToIpConverter(
                getPlayer().getAddress().getAddress().toString()).convert();
    }

    @Override
    public Player getPlayer() {
        return event.getPlayer();
    }
}
