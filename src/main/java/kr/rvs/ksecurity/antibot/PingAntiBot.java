package kr.rvs.ksecurity.antibot;

import kr.rvs.ksecurity.util.Lang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
public class PingAntiBot implements Listener {
    private final AtomicInteger counter;
    private final Set<String> addrSet = Collections.newSetFromMap(new WeakHashMap<>());

    public static String formattingHostname(String hostName) {
        int point = hostName.indexOf(':');
        if (point > -1) {
            if (hostName.substring(point).equals(":25565")) {
                hostName = hostName.substring(0, point);
            }
        }
        return hostName;
    }

    public PingAntiBot(AtomicInteger counter) {
        this.counter = counter;
    }

    @EventHandler
    public void onPing(ServerListPingEvent event) {
        addrSet.add(event.getAddress().toString());
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) throws UnknownHostException {
        Player player = event.getPlayer();
        String hostName = event.getHostname();
        InetAddress address = event.getAddress();
        if (!player.hasPermission("ksecurity.antibot.pass")
                && !address.isLoopbackAddress()
                && !address.isSiteLocalAddress()
                && !addrSet.contains(address.toString())) {
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, Lang.SHOULD_PING.withSpacingPrefix(
                    Lang.Key.VALUE, formattingHostname(hostName)
            ));
            counter.incrementAndGet();
        }
    }
}
