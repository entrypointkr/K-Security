package cloud.swiftnode.kspam.listener;

import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.abstraction.processor.SyncJoinProcessor;
import cloud.swiftnode.kspam.util.Static;
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
        SpamProcessor processor = new SyncJoinProcessor(executor, adapter);
        processor.process();
    }
}
