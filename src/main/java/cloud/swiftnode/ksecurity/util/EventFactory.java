package cloud.swiftnode.ksecurity.util;

import cloud.swiftnode.ksecurity.module.kanticheat.event.PlayerUseCheatEvent;
import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-02-13.
 */
public class EventFactory {
    public static PlayerUseCheatEvent createUseCheatEvent(Player player, PlayerUseCheatEvent.CheatType type) {
        return new PlayerUseCheatEvent(player, type);
    }
}
