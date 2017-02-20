package cloud.swiftnode.ksecurity.module.kanticheat.listener;

import cloud.swiftnode.ksecurity.module.kanticheat.event.PlayerUseCheatEvent;
import cloud.swiftnode.ksecurity.util.Config;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Static;
import cloud.swiftnode.ksecurity.util.StaticStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Junhyeong Lim on 2017-02-11.
 */
public class CheatListener implements Listener {
    @EventHandler
    public void onBug(PlayerUseCheatEvent e) {
        if (!e.getPlayer().isOp() && e.getType().isDeny()) {
            e.setCancelled(true);
            if (Config.isAcAlert()) {
                for (Player player : Static.getOnlinePlayers()) {
                    if (!player.isOp()
                            || StaticStorage.cheatAlertEscapeList.contains(player.getName())) {
                        continue;
                    }
                    player.sendMessage(Lang.USE_CHEAT.builder()
                            .addKey(Lang.Key.PLAYER_NAME, Lang.Key.VALUE)
                            .addVal(e.getPlayer().getName(), e.getType().name())
                            .build());
                }
            }
        }
    }
}
