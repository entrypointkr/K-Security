package cloud.swiftnode.kspam.listener;

import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.StaticStorage;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.plugin.Plugin;

/**
 * Created by Junhyeong Lim on 2017-01-12.
 */
public class PacketListener extends PacketAdapter {
    public PacketListener(Plugin plugin) {
        super(plugin, ListenerPriority.LOWEST, PacketType.Play.Server.LOGIN);
        Static.consoleMsg(Lang.PROTOCOL_LIB_DETECT.builder().prefix());
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        StaticStorage.validateSet.add(event.getPlayer().getName().toLowerCase());
    }
}
