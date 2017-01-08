package cloud.swiftnode.kspam.listener;

import cloud.swiftnode.kspam.abstraction.Processor;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.abstraction.processor.AsynchronousSpamProcessor;
import cloud.swiftnode.kspam.abstraction.processor.SynchronizeSpamProcessor;
import cloud.swiftnode.kspam.abstraction.runnable.PunishExecutor;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class PlayerListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(final PlayerLoginEvent e) {
        if (e.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            return;
        }
        final DeniableInfoAdapter adapter = new DeniableInfoAdapter(e);
        final PunishExecutor executor = new PunishExecutor();
        Processor sync = new SynchronizeSpamProcessor(adapter, executor);
        if (!sync.process()) {
            Static.runTaskAsync(new Runnable() {
                @Override
                public void run() {
                    adapter.setObj(e.getPlayer());
                    Processor async = new AsynchronousSpamProcessor(adapter, executor);
                    async.process();
                }
            });
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(ServerListPingEvent e) {
        DeniableInfoAdapter adapter = new DeniableInfoAdapter(e);
        PunishExecutor executor = new PunishExecutor();
        new SynchronizeSpamProcessor(adapter, executor).process();
    }
}
