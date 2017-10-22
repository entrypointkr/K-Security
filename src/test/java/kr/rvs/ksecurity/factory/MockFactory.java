package kr.rvs.ksecurity.factory;

import org.bukkit.Server;
import org.mockito.Mockito;

/**
 * Created by Junhyeong Lim on 2017-10-22.
 */
public class MockFactory extends Mockito {
    public static Server createMockServer() {
        Server server = mock(Server.class);
        when(server.getBukkitVersion()).thenReturn("1.12.2");
        return server;
    }
}
