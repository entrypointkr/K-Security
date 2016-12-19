package cloud.swiftnode.kspam.listener;

import cloud.swiftnode.kspam.runnable.CheckRunnable;
import cloud.swiftnode.kspam.runnable.ProcessRunnable;
import cloud.swiftnode.kspam.storage.CheckStorage;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.storage.VersionStorage;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class PlayerListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        Static.removePlayerInStorage(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(final PlayerLoginEvent e) {
        if (e.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            return;
        }
        final String ip = Static.convertToIp(e.getAddress());
        if (CheckStorage.getCachedIpList().contains(ip)) {
            e.setKickMessage(Lang.PREFIX + "\n" + Lang.KICK.toString());
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            return;
        }
        Static.runTaskAsync(new Runnable() {
            @Override
            public void run() {
                CheckStorage storage = new CheckStorage(ip);
                new CheckRunnable(storage).run();
                new ProcessRunnable(e.getPlayer(), storage.getResult()).run();
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!p.isOp()) {
            return;
        }
        VersionStorage storage = StaticStorage.getVersionStorage();
        if (storage == null) {
            return;
        }
        Static.msgLineLoop(p, Lang.PREFIX.toString() + Lang.NEW_VERSION + "\n" +
                Lang.VERSION.toString(Lang.PREFIX, storage.getCurrVer().toString(), storage.getNewVer().toString()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(ServerListPingEvent e) {
        String ip = Static.convertToIp(e.getAddress());
        if (CheckStorage.getCachedIpList().contains(ip)) {
            e.setMotd(Lang.MOTD.toString());
        }
    }
}