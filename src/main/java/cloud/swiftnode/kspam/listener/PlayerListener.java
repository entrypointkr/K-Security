package cloud.swiftnode.kspam.listener;

import cloud.swiftnode.kspam.abstraction.Processor;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.abstraction.processor.AsynchronousSpamProcessor;
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
        final DeniableInfoAdapter adapter = new DeniableInfoAdapter(e);
        Processor sync = new SynchronizeSpamProcessor(adapter, tracer);
        if (!sync.process()) {
            Static.runTaskAsync(new Runnable() {
                @Override
                public void run() {
                    adapter.setObj(e.getPlayer());
                    Processor async = new AsynchronousSpamProcessor(adapter, tracer);
                    async.process();
                }
            });
        }
    }
}
