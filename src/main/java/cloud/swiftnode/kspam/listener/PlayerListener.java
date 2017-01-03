package cloud.swiftnode.kspam.listener;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableAdapter;
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
        Tracer tracer = new Tracer();
        Deniable deniable = new DeniableAdapter(e);
        SpamProcessor sync = new SynchronizeSpamProcessor(deniable, tracer);
        if (!sync.process()) {
            final SpamProcessor async = new AsynchronizeSpamProcessor(new DeniableAdapter(e), tracer);
            Static.runTaskAsync(new Runnable() {
                @Override
                public void run() {
                    async.process();
                }
            });
        }
    }
}
