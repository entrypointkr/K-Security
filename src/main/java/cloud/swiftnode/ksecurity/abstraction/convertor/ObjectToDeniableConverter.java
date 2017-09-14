package cloud.swiftnode.ksecurity.abstraction.convertor;

import cloud.swiftnode.ksecurity.abstraction.ObjectConverter;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.Deniable;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.deniable.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
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
        } else if (obj instanceof AsyncPlayerPreLoginEvent) {
            return new PreLoginEventDeniable(delayed, (AsyncPlayerPreLoginEvent) obj);
        }
        throw new IllegalArgumentException("Unexpected argument " + obj.getClass().getName());
    }
}
