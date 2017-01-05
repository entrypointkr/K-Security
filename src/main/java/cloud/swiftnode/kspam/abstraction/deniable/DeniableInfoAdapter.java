package cloud.swiftnode.kspam.abstraction.deniable;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.StaticStorage;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.UUID;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class DeniableInfoAdapter implements Deniable, Info {
    private Object obj;
    private String lastInfo;

    public DeniableInfoAdapter(Object obj) {
        if (!(obj instanceof Event) &&
                !(obj instanceof Player)) {
            throw new IllegalArgumentException("Unexpected argument " + obj.getClass().getName());
        }
        this.obj = obj;
    }

    @Override
    public void deny() {
        final String kickMsg = Lang.DENY.toString();
        if (obj instanceof ServerListPingEvent) {
            ((ServerListPingEvent) obj).setMotd(Lang.MOTD.toString());
        } else if (obj instanceof PlayerLoginEvent) {
            ((PlayerLoginEvent) obj).disallow(PlayerLoginEvent.Result.KICK_BANNED, kickMsg);
        } else if (obj instanceof Cancellable) {
            ((Cancellable) obj).setCancelled(true);
        } else if (obj instanceof Player) {
            Static.runTask(new Runnable() {
                @Override
                public void run() {
                    ((Player) obj).kickPlayer(kickMsg);
                }
            });
        } else {
            throw new IllegalArgumentException("Unexpected argument " + obj.getClass().getName());
        }
    }

    @Override
    public String getIp() {
        if (obj instanceof ServerListPingEvent) {
            return lastInfo = parseIp(((ServerListPingEvent) obj).getAddress().toString());
        } else if (obj instanceof PlayerLoginEvent) {
            return lastInfo = parseIp(((PlayerLoginEvent) obj).getAddress().toString());
        }
        Player p = getPlayer();
        if (p != null) {
            return lastInfo = parseIp(p.getAddress().getAddress().toString());
        }
        return null;
    }

    @Override
    public String getUniqueId() throws RuntimeException {
        String uuid = null;
        Player p = getPlayer();
        if (p != null) {
            try {
                uuid = OfflinePlayer.class.getDeclaredMethod("getUniqueId").invoke(p).toString();
                lastInfo = uuid;
            } catch (Throwable t) {
                throw new IllegalStateException("UUID Doesn't support.");
            }
        }
        return uuid;
    }

    @Override
    public String getName() {
        Player p = getPlayer();
        return p != null ? lastInfo = p.getName() : null;
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

    public void caching() {
        StaticStorage.cachedSet.add(lastInfo);
        lastInfo = null;
    }

    private String parseIp(String ip) {
        return ip.substring(ip.indexOf("/") + 1);
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public void setLastInfo(String lastInfo) {
        this.lastInfo = lastInfo;
    }
}
