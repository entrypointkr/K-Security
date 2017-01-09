package cloud.swiftnode.kspam.listener;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.convertor.ObjectToDeniableConverter;
import cloud.swiftnode.kspam.abstraction.convertor.ObjectToInfoConverter;
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
        Deniable deniable = new ObjectToDeniableConverter(e).convert();
        Info info = new ObjectToInfoConverter(e).convert();
        DeniableInfoAdapter adapter = new DeniableInfoAdapter(deniable, info);
        SpamProcessor processor = new SyncLoginProcessor(executor, adapter);
        processor.process();
    }
}
