package cloud.swiftnode.kspam.abstraction.convertor;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.ObjectConverter;
import cloud.swiftnode.kspam.abstraction.deniable.CancellableDeniable;
import cloud.swiftnode.kspam.abstraction.deniable.EmptyDeniable;
import cloud.swiftnode.kspam.abstraction.deniable.LoginEventDeniable;
import cloud.swiftnode.kspam.abstraction.deniable.PlayerDeniable;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerLoginEvent;

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
        }
        throw new IllegalArgumentException("Unexpected argument " + obj.getClass().getName());
    }
}
