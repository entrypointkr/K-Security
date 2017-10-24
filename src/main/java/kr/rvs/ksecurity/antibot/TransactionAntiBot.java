package kr.rvs.ksecurity.antibot;

import com.comphenix.protocol.Packets;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.packet.PacketRegistry;
import kr.rvs.ksecurity.factory.LegacyPacketFactory;
import kr.rvs.ksecurity.factory.PacketFactory;
import kr.rvs.ksecurity.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
@SuppressWarnings("deprecation")
public class TransactionAntiBot extends PacketAdapter implements Listener {
    private final PacketFactory factory;
    private final AtomicInteger counter;
    private final CGLIBPlayerCacher cacher = new CGLIBPlayerCacher();
    private final PacketIgnore ignore = new PacketIgnore();
    private final TransactionLatch latch = new TransactionLatch();
    private final PacketHolder holder = new PacketHolder();

    public static Set<Integer> getAllPackets() {
        Set<Integer> ids = new HashSet<>();
        ids.addAll(PacketRegistry.getClientPackets());
        ids.addAll(PacketRegistry.getServerPackets());
        return ids;
    }

    public static void init(Plugin plugin, AtomicInteger counter) {
        TransactionAntiBot antiBot = new TransactionAntiBot(plugin, new LegacyPacketFactory(), counter);
        Bukkit.getPluginManager().registerEvents(antiBot, plugin);
        ProtocolLibrary.getProtocolManager().addPacketListener(antiBot);
    }

    public TransactionAntiBot(Plugin plugin, PacketFactory factory, AtomicInteger counter) {
        super(plugin, ConnectionSide.BOTH, getAllPackets().toArray(new Integer[0]));
        this.factory = factory;
        this.counter = counter;
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        Player player = event.getPlayer();
        int id = event.getPacketID();
        holder.holdIfPresent(event);
        if (id == Packets.Client.HANDSHAKE) {
            cacher.caching(player);
        }
        if (id == Packets.Client.TRANSACTION) {
            PacketContainer packet = event.getPacket();
            int pId = packet.getShorts().read(0);
            if (pId == Short.MIN_VALUE) {
                latch.countDown(player);
            }
        }
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        ignore.ignore(event);
    }

    private void sendPacket(Player player, PacketContainer packetContainer) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(e);
        }
    }

    @EventHandler
    public void onAsync(AsyncPlayerPreLoginEvent event) {
        cacher.removeAndGet(event.getAddress()).ifPresent(player -> {
            sendPacket(player, factory.createLoginPacket());
            Bukkit.getScheduler().runTask(plugin, () -> ignore.addIgnore(player, Packets.Server.LOGIN));
            holder.addPlayer(player);

            sendPacket(player, factory.createTransactionPacket(0, Short.MIN_VALUE, false));
            if (latch.await(player)) {
                holder.flush(player);
            } else {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Lang.BOT_DETECT.withSpacingPrefix());
                counter.incrementAndGet();
            }
            holder.release(player);
            latch.release(player);
        });
    }
}
