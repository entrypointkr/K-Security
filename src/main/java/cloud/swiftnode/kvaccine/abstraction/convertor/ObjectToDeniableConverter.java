package cloud.swiftnode.kvaccine.abstraction.convertor;

import cloud.swiftnode.kvaccine.abstraction.Deniable;
import cloud.swiftnode.kvaccine.abstraction.ObjectConverter;
import cloud.swiftnode.kvaccine.abstraction.deniable.CancellableDeniable;
import cloud.swiftnode.kvaccine.abstraction.deniable.EmptyDeniable;
import cloud.swiftnode.kvaccine.abstraction.deniable.LoginEventDeniable;
import cloud.swiftnode.kvaccine.abstraction.deniable.PingDeniable;
import cloud.swiftnode.kvaccine.abstraction.deniable.PlayerDeniable;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class ObjectToDeniableConverter extends ObjectConverter<Deniable> {
    private boolean delayed;

    public ObjectToDeniableConverter(Object obj, boolean delayed) {
        super(obj);
        this.delayed = delayed;
    }

    @Override
    public Deniable convert() {
        if (obj instanceof Cancellable) {
            return new CancellableDeniable(delayed, (Cancellable) obj);
        } else if (obj instanceof PlayerLoginEvent) {
            return new LoginEventDeniable(delayed, ((PlayerLoginEvent) obj));
        } else if (obj instanceof Player) {
            return new PlayerDeniable((Player) obj);
        } else if (obj instanceof String) {
            return new EmptyDeniable();
        } else if (obj instanceof ServerListPingEvent) {
            return new PingDeniable(delayed, (ServerListPingEvent) obj);
        }
        throw new IllegalArgumentException("Unexpected argument " + obj.getClass().getName());
    }
}
