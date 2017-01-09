package temp.cloud.swiftnode.kspam.listener;

import temp.cloud.swiftnode.kspam.abstraction.Processor;
import temp.cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import temp.cloud.swiftnode.kspam.abstraction.executor.FirstKickExecutor;
import temp.cloud.swiftnode.kspam.abstraction.processor.AsyncLoginSpamProcessor;
import temp.cloud.swiftnode.kspam.abstraction.processor.SyncJoinSpamProcessor;
import temp.cloud.swiftnode.kspam.abstraction.processor.SyncLoginSpamProcessor;
import temp.cloud.swiftnode.kspam.abstraction.executor.PunishExecutor;
import temp.cloud.swiftnode.kspam.util.Static;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
        Processor sync = new SyncLoginSpamProcessor(adapter, executor);
        if (!sync.process()) {
            Static.runTaskAsync(new Runnable() {
                @Override
                public void run() {
                    adapter.setObj(e.getPlayer());
                    Processor async = new AsyncLoginSpamProcessor(adapter, executor);
                    async.process();
                }
            });
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(ServerListPingEvent e) {
        DeniableInfoAdapter adapter = new DeniableInfoAdapter(e);
        PunishExecutor executor = new PunishExecutor();
        new SyncLoginSpamProcessor(adapter, executor).process();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        DeniableInfoAdapter adapter = new DeniableInfoAdapter(e.getPlayer());
        Processor processor = new SyncJoinSpamProcessor(adapter, new FirstKickExecutor());
        processor.process();
    }
}
