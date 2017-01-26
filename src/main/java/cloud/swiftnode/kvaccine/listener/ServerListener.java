package cloud.swiftnode.kvaccine.listener;

import cloud.swiftnode.kvaccine.abstraction.SpamExecutor;
import cloud.swiftnode.kvaccine.abstraction.SpamProcessor;
import cloud.swiftnode.kvaccine.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kvaccine.abstraction.processor.SyncPingProcessor;
import cloud.swiftnode.kvaccine.util.Static;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by Junhyeong Lim on 2017-01-13.
 */
public class ServerListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(ServerListPingEvent e) {
        SpamExecutor executor = Static.getDefaultExecutor();
        DeniableInfoAdapter adapter = new DeniableInfoAdapter(false, e);
        SpamProcessor processor = new SyncPingProcessor(executor, adapter);
        processor.process();
    }
}
