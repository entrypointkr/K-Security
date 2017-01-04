package cloud.swiftnode.kspam.abstraction.deniable;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.Info;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class DeniableInfoAdapter implements Deniable, Info {
    private Object obj;

    public DeniableInfoAdapter(Object obj) {
        if (!(obj instanceof Event) &&
                !(obj instanceof Player)) {
            throw new IllegalArgumentException("Unexpected argument " + obj.getClass().getName());
        }
        this.obj = obj;
    }

    @Override
    public void deny() {
        String kickMsg = "You have been blocked by K-SPAM.";
        if (obj instanceof Cancellable) {
            ((Cancellable) obj).setCancelled(true);
        } else if (obj instanceof PlayerLoginEvent) {
            ((PlayerLoginEvent) obj).disallow(PlayerLoginEvent.Result.KICK_OTHER, kickMsg);
        } else if (obj instanceof Player) {
            ((Player) obj).kickPlayer(kickMsg);
        } else {
            throw new IllegalArgumentException("Unexpected argument " + obj.getClass().getName());
        }
    }

    @Override
    public String getIp() {
        Player p = getPlayer();
        if (p != null) {
            String ip = p.getAddress().getAddress().toString();
            return ip.substring(ip.indexOf("/") + 1);
        }
        return null;
    }

    @Override
    public UUID getUUID() throws RuntimeException {
        UUID uuid = null;
        Player p = getPlayer();
        if (p != null) {
            try {
                uuid = (UUID) OfflinePlayer.class.getDeclaredMethod("getUniqueId").invoke(p);
            } catch (Throwable t) {
                throw new RuntimeException("UUID Doesn't support version.");
            }
        }
        return uuid;
    }

    @Override
    public String getName() {
        Player p = getPlayer();
        return p != null ? p.getName() : null;
    }

    private Player getPlayer() {
        if (obj instanceof PlayerEvent) {
            return ((PlayerEvent) obj).getPlayer();
        } else if (obj instanceof Player) {
            return (Player) obj;
        } else {
            return null;
        }
    }

    public Object getObj() {
        return obj;
    }
}
