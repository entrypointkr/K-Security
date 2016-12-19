package cloud.swiftnode.kspam.listener;

import cloud.swiftnode.kspam.runnable.CheckRunnable;
import cloud.swiftnode.kspam.runnable.ProcessRunnable;
import cloud.swiftnode.kspam.storage.CheckStorage;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
        String ip = Static.convertToIp(e.getAddress());
        if (CheckStorage.getCachedIpList().contains(ip)) {
            e.setKickMessage(Lang.PREFIX + "\n" + Lang.KICK.toString());
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
            return;
        }
        final CheckStorage storage = new CheckStorage(Static.convertToIp(e.getAddress()));
        Static.runTaskAsync(new Runnable() {
            @Override
            public void run() {
                new CheckRunnable(storage).run();
                new ProcessRunnable(e.getPlayer(), storage.getResult()).run();
            }
        });
    }
}