package temp.cloud.swiftnode.kspam.abstraction.deniable;

import temp.cloud.swiftnode.kspam.abstraction.Deniable;
import temp.cloud.swiftnode.kspam.abstraction.Info;
import temp.cloud.swiftnode.kspam.util.Lang;
import temp.cloud.swiftnode.kspam.util.Static;
import temp.cloud.swiftnode.kspam.util.StaticStorage;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class DeniableInfoAdapter implements Deniable, Info {
    private Object obj;
    private String lastInfo;
    private String kickMsg;

    public DeniableInfoAdapter(Object obj) {
        this(obj, Lang.DENY.toString());
    }

    public DeniableInfoAdapter(Object obj, String kickMsg) {
        if (!(obj instanceof Event) &&
                !(obj instanceof Player) && !(obj instanceof String)) {
            throw new IllegalArgumentException("Unexpected argument " + obj.getClass().getName());
        }
        this.obj = obj;
        this.kickMsg = kickMsg;
    }

    @Override
    public void deny() {
        if (obj instanceof ServerListPingEvent) {
            ((ServerListPingEvent) obj).setMotd(Lang.MOTD.toString());
        } else if (obj instanceof PlayerLoginEvent) {
            ((PlayerLoginEvent) obj).disallow(PlayerLoginEvent.Result.KICK_OTHER, kickMsg);
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
        } else if (obj instanceof String) {
            return (String) obj;
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
        if (obj instanceof String) {
            return (String) obj;
        }
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
        if (obj instanceof String) {
            return (String) obj;
        }
        Player p = getPlayer();
        return p != null ? lastInfo = p.getName() : null;
    }

    public Player getPlayer() {
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

    public void setKickMsg(String kickMsg) {
        this.kickMsg = kickMsg;
    }
}
