package cloud.swiftnode.ksecurity.module.kanticheat.listener;

import cloud.swiftnode.ksecurity.module.kanticheat.event.PlayerCheatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Junhyeong Lim on 2017-02-11.
 */
public class CheatListener implements Listener {
    @EventHandler
    public void onBug(PlayerCheatEvent e) {
        if (e.getType() == PlayerCheatEvent.CheatType.SHOPKEEPER) {
            // TODO: Shopkeeper, Version check
        }
    }
}
