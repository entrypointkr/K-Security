package cloud.swiftnode.kspam.listener;

import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.abstraction.executor.DebugSpamExecutor;
import cloud.swiftnode.kspam.abstraction.executor.PunishSpamExecutor;
import cloud.swiftnode.kspam.abstraction.processor.AsyncLoginProcessor;
import cloud.swiftnode.kspam.abstraction.processor.SyncJoinProcessor;
import cloud.swiftnode.kspam.abstraction.processor.SyncLoginProcessor;
import cloud.swiftnode.kspam.util.Config;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class PlayerListener implements Listener {
    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        if (e.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            return;
        }
        final SpamExecutor executor = getExecutor();
        final DeniableInfoAdapter adapter = new DeniableInfoAdapter(false, e);
        final Player player = e.getPlayer();
        SpamProcessor processor = new SyncLoginProcessor(executor, adapter);
        if (!processor.process()) {
            adapter.setObj(player);
            adapter.setDelayed(true);
            Static.runTaskLaterAsync(new Runnable() {
                @Override
                public void run() {
                    if (player.isOnline()) {
                        new AsyncLoginProcessor(executor, adapter).process();
                    }
                }
            }, 20L);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        SpamExecutor executor = getExecutor();
        DeniableInfoAdapter adapter = new DeniableInfoAdapter(true, e.getPlayer());
        SpamProcessor processor = new SyncJoinProcessor(executor, adapter);
        processor.process();
    }

    public SpamExecutor getExecutor() {
        if (Static.getConfig().getBoolean(Config.DEBUG_MODE, false)) {
            return new DebugSpamExecutor(new PunishSpamExecutor());
        } else {
            return new PunishSpamExecutor();
        }
    }
}
