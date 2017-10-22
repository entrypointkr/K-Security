package kr.rvs.ksecurity.antibot;

import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
public class CGLIBPlayerCacher {
    private final Map<String, Player> playerMap = new WeakHashMap<>();

    public void caching(Player player) {
        playerMap.put(player.getAddress().getAddress().toString(), player);
    }

    public Optional<Player> removeAndGet(InetAddress address) {
        return Optional.ofNullable(playerMap.remove(address.toString()));
    }
}
