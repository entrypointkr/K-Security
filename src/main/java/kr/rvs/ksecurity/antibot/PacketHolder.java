package kr.rvs.ksecurity.antibot;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.collect.Sets;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
public class PacketHolder {
    private final Map<String, Set<PacketContainer>> packetMap = new ConcurrentHashMap<>();

    private void receivePacket(Player player, PacketContainer packetContainer) {
        try {
            ProtocolLibrary.getProtocolManager().recieveClientPacket(player, packetContainer);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    private Optional<Set<PacketContainer>> get(Player player) {
        return Optional.ofNullable(packetMap.get(player.getName()));
    }

    public void addPlayer(Player player) {
        packetMap.put(player.getName(), Collections.newSetFromMap(new ConcurrentHashMap<>()));
    }

    public void holdIfPresent(PacketEvent event) {
        get(event.getPlayer()).ifPresent(packets -> {
            event.setCancelled(true);
            packets.add(event.getPacket());
        });
    }

    public void flush(Player player) {
        get(player).ifPresent(packets -> {
            for (PacketContainer packet : packets) {
                receivePacket(player, packet);
            }
        });
    }

    public void release(Player player) {
        packetMap.remove(player.getName());
    }
}
