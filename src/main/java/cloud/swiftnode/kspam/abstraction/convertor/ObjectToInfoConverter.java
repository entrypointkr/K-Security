package cloud.swiftnode.kspam.abstraction.convertor;

import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.ObjectConverter;
import cloud.swiftnode.kspam.abstraction.info.EventInfo;
import cloud.swiftnode.kspam.abstraction.info.PacketInfo;
import cloud.swiftnode.kspam.abstraction.info.PingInfo;
import cloud.swiftnode.kspam.abstraction.info.PlayerInfo;
import cloud.swiftnode.kspam.abstraction.info.StringInfo;
import cloud.swiftnode.kspam.util.StaticStorage;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class ObjectToInfoConverter extends ObjectConverter<Info> {
    public ObjectToInfoConverter(Object obj) {
        super(obj);
    }

    @Override
    public Info convert() {
        if (obj instanceof Player) {
            return new PlayerInfo((Player) obj);
        } else if (obj instanceof PlayerEvent) {
            return new EventInfo((PlayerEvent) obj);
        } else if (obj instanceof String) {
            return new StringInfo((String) obj);
        } else if (StaticStorage.protocolLib && ProtocolLibEvent.getInfo(obj) != null) {
            return ProtocolLibEvent.getInfo(obj);
        } else if (obj instanceof ServerListPingEvent) {
            return new PingInfo((ServerListPingEvent) obj);
        }
        throw new IllegalArgumentException("Unexpected argument " + obj.getClass().getName());
    }

    static class ProtocolLibEvent {
        static Info getInfo(Object obj) {
            if (obj instanceof PacketEvent) {
                return new PacketInfo((PacketEvent) obj);
            }
            return null;
        }
    }
}
