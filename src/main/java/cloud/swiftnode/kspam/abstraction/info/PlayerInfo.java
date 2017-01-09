package cloud.swiftnode.kspam.abstraction.info;

import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.convertor.StringToIpConverter;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class PlayerInfo implements Info {
    private Player player;

    public PlayerInfo(Player player) {
        this.player = player;
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public String getIp() {
        return new StringToIpConverter(
                player.getAddress().getAddress().toString()).convert();
    }

    @Override
    public String getUniqueId() throws IllegalStateException {
        String uuid = null;
        if (player != null) {
            try {
                uuid = OfflinePlayer.class.getDeclaredMethod("getUniqueId").invoke(player).toString();
            } catch (Throwable t) {
                throw new IllegalStateException("UUID Doesn't support.");
            }
        }
        return uuid;
    }
}
