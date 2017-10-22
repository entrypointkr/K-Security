package kr.rvs.ksecurity.antibot;

import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

/**
 * Created by Junhyeong Lim on 2017-09-05.
 */
public class PacketMonitor extends PacketAdapter {
    public PacketMonitor(Plugin plugin) {
        super(plugin, ConnectionSide.BOTH, TransactionAntiBot.getAllPackets().toArray(new Integer[0]));
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        System.out.println("<- " + event.getPacket().getHandle().getClass().getSimpleName());
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        System.out.println("-> " + event.getPacket().getHandle().getClass().getSimpleName());
    }
}
