package cloud.swiftnode.kspam.listener;

import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.abstraction.executor.BaseSpamExecutor;
import cloud.swiftnode.kspam.abstraction.processor.SyncLoginProcessor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class PlayerListener implements Listener {
    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        SpamExecutor executor = new BaseSpamExecutor();
        DeniableInfoAdapter adapter = new DeniableInfoAdapter(e);
        SpamProcessor processor = new SyncLoginProcessor(executor, adapter);
        processor.process();
    }
}
