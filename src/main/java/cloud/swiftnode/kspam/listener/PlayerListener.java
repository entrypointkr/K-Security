package cloud.swiftnode.kspam.listener;

import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.abstraction.processor.AsynchronizeSpamProcessor;
import cloud.swiftnode.kspam.abstraction.processor.SynchronizeSpamProcessor;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.Tracer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class PlayerListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(final PlayerLoginEvent e) {
        if (e.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            return;
        }
        final Tracer tracer = new Tracer();
        DeniableInfoAdapter adapter = new DeniableInfoAdapter(e);
        SpamProcessor sync = new SynchronizeSpamProcessor(adapter, tracer);
        if (!sync.process()) {
            Static.runTaskAsync(new Runnable() {
                @Override
                public void run() {
                    SpamProcessor async = new AsynchronizeSpamProcessor(new DeniableInfoAdapter(e), tracer);
                    async.process();
                }
            });
        }
    }
}
