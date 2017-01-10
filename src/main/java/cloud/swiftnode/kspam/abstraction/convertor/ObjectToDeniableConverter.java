package cloud.swiftnode.kspam.abstraction.convertor;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.ObjectConverter;
import cloud.swiftnode.kspam.abstraction.deniable.CancellableDeniable;
import cloud.swiftnode.kspam.abstraction.deniable.LoginEventDeniable;
import cloud.swiftnode.kspam.abstraction.deniable.PlayerDeniable;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class ObjectToDeniableConverter extends ObjectConverter<Deniable> {
    public ObjectToDeniableConverter(Object obj) {
        super(obj);
    }

    @Override
    public Deniable convert() {
        if (obj instanceof Cancellable) {
            return new CancellableDeniable((Cancellable) obj);
        } else if (obj instanceof PlayerLoginEvent) {
            return new LoginEventDeniable(((PlayerLoginEvent) obj));
        } else if (obj instanceof Player) {
            return new PlayerDeniable((Player) obj);
        }
        throw new IllegalArgumentException("Unexpected argument " + obj.getClass().getName());
    }
}
