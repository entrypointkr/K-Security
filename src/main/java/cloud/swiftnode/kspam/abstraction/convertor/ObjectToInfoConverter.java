package cloud.swiftnode.kspam.abstraction.convertor;

import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.ObjectConverter;
import cloud.swiftnode.kspam.abstraction.info.EventInfo;
import cloud.swiftnode.kspam.abstraction.info.PlayerInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

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
        }
        throw new IllegalArgumentException("Unexpected argument " + obj.getClass().getName());
    }
}
