package cloud.swiftnode.kspam.abstraction.deniable;

import cloud.swiftnode.kspam.abstraction.Deniable;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class DeniableWrapper implements Deniable {
    private Object object;

    public DeniableWrapper(Object object) {
        if (!(object instanceof org.bukkit.event.Cancellable) &&
                !(object instanceof Player)) {
            throw new IllegalArgumentException("Unexpected argument " + object.getClass().getName());
        }
        this.object = object;
    }

    @Override
    public void deny() {
        String kickMsg = "You have been blocked by K-SPAM.";
        if (object instanceof Cancellable) {
            ((Cancellable) object).setCancelled(true);
        } else if (object instanceof PlayerLoginEvent) {
            ((PlayerLoginEvent) object).disallow(PlayerLoginEvent.Result.KICK_OTHER, kickMsg);
        } else if (object instanceof Player) {
            ((Player) object).kickPlayer(kickMsg);
        } else {
            throw new IllegalArgumentException("Unexpected argument " + object.getClass().getName());
        }
    }

    public Object getObject() {
        return object;
    }
}
