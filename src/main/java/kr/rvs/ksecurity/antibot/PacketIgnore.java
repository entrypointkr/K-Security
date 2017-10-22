package kr.rvs.ksecurity.antibot;

import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
public class PacketIgnore {
    private final Map<String, Integer> ignorePacketMap = new HashMap<>();

    private int def(Integer val, int def) {
        return val != null ? val : def;
    }

    public void addIgnore(Player player, Integer id) {
        ignorePacketMap.put(player.getName(), id);
    }

    public void ignore(PacketEvent event) {
        PacketContainer packet = event.getPacket();
        Player player = event.getPlayer();
        if (packet.getID() == def(ignorePacketMap.remove(player.getName()), -1)) {
            event.setCancelled(true);
        }
    }
}
