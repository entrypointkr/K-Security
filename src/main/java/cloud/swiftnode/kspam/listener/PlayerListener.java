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
        final SpamExecutor executor = getExecutor();
        final DeniableInfoAdapter adapter = new DeniableInfoAdapter(false, e);
        SpamProcessor processor = new SyncLoginProcessor(executor, adapter);
        if (!processor.process()) {
            adapter.setObj(e.getPlayer());
            adapter.setDelayed(true);
            Static.runTaskAsync(new Runnable() {
                @Override
                public void run() {
                    new AsyncLoginProcessor(executor, adapter).process();
                }
            });
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
