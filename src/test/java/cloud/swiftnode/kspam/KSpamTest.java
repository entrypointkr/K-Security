package cloud.swiftnode.kspam;

import cloud.swiftnode.kspam.abstraction.MockPlayer;
import cloud.swiftnode.kspam.abstraction.mock.MockPlugin;
import cloud.swiftnode.kspam.abstraction.MockServer;
import cloud.swiftnode.kspam.listener.PlayerListener;
import cloud.swiftnode.kspam.listener.ServerListener;
import cloud.swiftnode.kspam.util.StaticUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class KSpamTest {
    @Before
    public void initTest() throws NoSuchFieldException, IllegalAccessException {
        // Injection
        Field serverField = Bukkit.class.getDeclaredField("server");
        Field instField = KSpam.class.getDeclaredField("inst");

        serverField.setAccessible(true);
        instField.setAccessible(true);

        serverField.set(null, new MockServer());
        instField.set(null, new MockPlugin());
    }

    @Test
    public void randomTest() throws UnknownHostException, IllegalAccessException, NoSuchFieldException {
        int tryCount = 5;

        for (int i = 0; i < tryCount; i++) {
            // Element
            InetAddress addr = StaticUtil.getRandomAddr();
            Player player = new MockPlayer(StaticUtil.getRandomName(), addr);

            // Print info
            System.out.println("=============== Random Test Counting (" + (i + 1) + "/" + tryCount + ") ===============");
            System.out.println("NAME: " + player.getName());
            System.out.println("UUID: " + player.getUniqueId());
            System.out.println("IP: " + player.getAddress().getAddress());

            // PlayerEvent
            PlayerLoginEvent loginEvent = new PlayerLoginEvent(player, "12.32.12.32", addr);
            // ServerEvent
            ServerListPingEvent pingEvent = new ServerListPingEvent(addr, "test motd", 1, 10);

            // PlayerListener
            PlayerListener playerListener = new PlayerListener();
            playerListener.onLogin(loginEvent);

            // ServerListener
            ServerListener serverListener = new ServerListener();
            serverListener.onPing(pingEvent);
        }
    }
}
