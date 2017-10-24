package kr.rvs.ksecurity.antibot;

import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
public class CGLIBPlayerCacher {
    private final Map<String, Player> playerMap = new HashMap<>();

    public void caching(Player player) {
        playerMap.put(player.getAddress().getAddress().toString(), player);
    }

    public Optional<Player> removeAndGet(InetAddress address) {
        return Optional.ofNullable(playerMap.remove(address.toString()));
    }

    public void release(Player player) {
        playerMap.remove(player.getAddress().getAddress().toString());
    }
}
