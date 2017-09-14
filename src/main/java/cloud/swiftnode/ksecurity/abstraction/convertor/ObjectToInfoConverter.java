package cloud.swiftnode.ksecurity.abstraction.convertor;

import cloud.swiftnode.ksecurity.abstraction.ObjectConverter;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.Info;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.info.EventInfo;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.info.PingInfo;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.info.PlayerInfo;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.info.StringInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
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
        } else if (obj instanceof ServerListPingEvent) {
            return new PingInfo((ServerListPingEvent) obj);
        } else if (obj instanceof AsyncPlayerPreLoginEvent) {
            AsyncPlayerPreLoginEvent event = (AsyncPlayerPreLoginEvent) obj;
            String addr = event.getAddress().toString();
            return new StringInfo(new StringToIpConverter(addr).convert(), "null", event.getName());
        }
        throw new IllegalArgumentException("Unexpected argument " + obj.getClass().getName());
    }
}
