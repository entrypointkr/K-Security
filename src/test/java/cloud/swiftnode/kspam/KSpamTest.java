package cloud.swiftnode.kspam;

import cloud.swiftnode.kspam.abstraction.MockPlayer;
import cloud.swiftnode.kspam.abstraction.MockPlugin;
import cloud.swiftnode.kspam.abstraction.MockServer;
import cloud.swiftnode.kspam.listener.PlayerListener;
import cloud.swiftnode.kspam.listener.ServerListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.junit.Test;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class KSpamTest {
    @Test
    public void prcsTest() throws UnknownHostException, IllegalAccessException, NoSuchFieldException {
        // Injection
        Field serverField = Bukkit.class.getDeclaredField("server");
        Field instField = KSpam.class.getDeclaredField("INSTANCE");

        serverField.setAccessible(true);
        instField.setAccessible(true);

        serverField.set(null, new MockServer());
        instField.set(null, new MockPlugin());

        // Element
        Player player = new MockPlayer();
        InetAddress addr = InetAddress.getByAddress(new byte[]{12, 32, 12, 32});

        // PlayerEvent
        PlayerLoginEvent loginEvent = new PlayerLoginEvent(player, "12.32.12.32", addr);
        PlayerJoinEvent joinEvent = new PlayerJoinEvent(player, "Test join");
        PlayerQuitEvent quitEvent = new PlayerQuitEvent(player, "Test quit");

        // ServerEvent
        ServerListPingEvent pingEvent = new ServerListPingEvent(addr, "test motd", 1, 10);

        // PlayerListener
        PlayerListener playerListener = new PlayerListener();
        playerListener.onLogin(loginEvent);
        playerListener.onJoin(joinEvent);
        playerListener.onQuit(quitEvent);

        // ServerListener
        ServerListener serverListener = new ServerListener();
        serverListener.onPing(pingEvent);
    }
}
