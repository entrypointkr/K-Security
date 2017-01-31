package cloud.swiftnode.ksecurity.module.kspam.abstraction.info;

import cloud.swiftnode.ksecurity.abstraction.convertor.StringToIpConverter;
import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class PlayerInfo extends AbstractInfo {
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
    public Player getPlayer() {
        return player;
    }
}
