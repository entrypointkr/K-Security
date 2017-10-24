package kr.rvs.ksecurity.factory;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.mockito.Mockito;

import java.net.InetSocketAddress;
import java.util.UUID;

/**
 * Created by Junhyeong Lim on 2017-10-22.
 */
public class MockFactory extends Mockito {
    public static Server createMockServer() {
        Server server = mock(Server.class);
        when(server.getBukkitVersion()).thenReturn("1.12.2");
        return server;
    }

    public static Player createMockPlayer(String name, UUID uuid, String address) {
        Player player = mock(Player.class);
        when(player.getName()).thenReturn(name);
        when(player.getUniqueId()).thenReturn(uuid);
        when(player.getAddress()).thenReturn(new InetSocketAddress(address, 0));

        return player;
    }
}
