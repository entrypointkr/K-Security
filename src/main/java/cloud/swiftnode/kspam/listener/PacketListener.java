package cloud.swiftnode.kspam.listener;

import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.abstraction.processor.SyncLoginProcessor;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.entity.Player;
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
    public void onPacketReceiving(PacketEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            return;
        }
        SyncLoginProcessor processor = new SyncLoginProcessor(
                Static.getDefaultExecutor(), new DeniableInfoAdapter(false, event));
        processor.process();
    }
}
