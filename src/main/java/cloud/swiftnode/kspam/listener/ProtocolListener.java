package cloud.swiftnode.kspam.listener;

import cloud.swiftnode.kspam.util.StaticStorage;
import com.comphenix.protocol.events.ConnectionSide;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

/**
 * Created by Junhyeong Lim on 2017-01-12.
 */
public class ProtocolListener extends PacketAdapter {
    @SuppressWarnings("deprecation")
    public ProtocolListener(Plugin plugin) {
        super(plugin, ConnectionSide.SERVER_SIDE, ListenerPriority.LOWEST, 35);
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        StaticStorage.validateSet.add(event.getPlayer().getName().toLowerCase());
    }
}
