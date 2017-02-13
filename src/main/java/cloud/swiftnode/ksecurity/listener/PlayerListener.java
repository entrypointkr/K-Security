package cloud.swiftnode.ksecurity.listener;

import cloud.swiftnode.ksecurity.module.kanticheat.event.PlayerUseCheatEvent;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.processor.AsyncLoginProcessor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.processor.SyncLoginProcessor;
import cloud.swiftnode.ksecurity.util.EventFactory;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Static;
import cloud.swiftnode.ksecurity.util.StaticStorage;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class PlayerListener implements Listener {
    // KSPAM
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(PlayerLoginEvent e) {
        if (e.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            return;
        }
        final SpamExecutor executor = Static.getDefaultExecutor();
        final DeniableInfoAdapter adapter = new DeniableInfoAdapter(false, e);
        final Player player = e.getPlayer();
        SpamProcessor processor = new SyncLoginProcessor(executor, adapter);
        if (!processor.process()) {
            adapter.setObj(player);
            adapter.setDelayed(true);
            Static.runTaskLaterAsync(() -> {
                if (player.isOnline()) {
                    new AsyncLoginProcessor(executor, adapter).process();
                }
            }, 20L);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent e) {
        // Check Opable
        Static.checkOpable(e.getPlayer());

        /*
        K-Security 는 AGPL 라이선스이며 개발자, 플러그인 정보, 소스 제공이 의무입니다.
        밑 메세지 전송 코드를 제거 시 법적 책임을 물을 수 있습니다.
        */
        Player player = e.getPlayer();
        player.sendMessage(Lang.PLUGIN_INTRO.builder().build(false));

        // Update notification
        if (player.isOp() && StaticStorage.getNewVer().after(StaticStorage.getCurrVer())) {
            player.sendMessage(Lang.UPDATE_INFO_NEW.builder().build());
            player.sendMessage(Lang.NEW_VERSION.builder()
                    .single(Lang.Key.NEW_VERSION, StaticStorage.getNewVer())
                    .build());
            player.sendMessage(Lang.CURRENT_VERSION.builder()
                    .single(Lang.Key.KSEC_VERSION, StaticStorage.getCurrVer())
                    .build());
            player.sendMessage(Lang.DOWNLOAD_URL.builder().build());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onClick(InventoryClickEvent e) {
        HumanEntity human = e.getWhoClicked();
        if (human.getInventory().getType() == InventoryType.MERCHANT
                || human.getOpenInventory().getTopInventory() != null) {
            if (e.getAction() == InventoryAction.COLLECT_TO_CURSOR
                    || e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                PlayerUseCheatEvent event = createEvent((Player) human, PlayerUseCheatEvent.CheatType.SHOPKEEPER);
                Bukkit.getPluginManager().callEvent(event);
                e.setCancelled(event.isCancelled());
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (block != null && block.getState() instanceof InventoryHolder) {
            InventoryHolder holder = (InventoryHolder) block.getState();
            List<HumanEntity> humanList = holder.getInventory().getViewers();
            if (humanList != null && humanList.size() > 0) {
                PlayerUseCheatEvent event = createEvent(e.getPlayer(), PlayerUseCheatEvent.CheatType.FREECAM);
                Bukkit.getPluginManager().callEvent(event);
                e.setCancelled(event.isCancelled());
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent e) {
        String msg = e.getMessage();
        if (msg.contains("&k") || msg.contains("§k")) {
            PlayerUseCheatEvent event = createEvent(e, PlayerUseCheatEvent.CheatType.CRASH_COLOR);
            callEvent(event);
            e.setCancelled(event.isCancelled());
            return;
        }

        if (msg.contains("<") && msg.contains(">")) {
            PlayerUseCheatEvent event = createEvent(e, PlayerUseCheatEvent.CheatType.VARIABLE_TRIGGER);
            callEvent(event);
            e.setCancelled(event.isCancelled());
        }
    }

    private PlayerUseCheatEvent createEvent(PlayerEvent event, PlayerUseCheatEvent.CheatType type) {
        return createEvent(event.getPlayer(), type);
    }

    private PlayerUseCheatEvent createEvent(Player player, PlayerUseCheatEvent.CheatType type) {
        return EventFactory.createUseCheatEvent(player, type);
    }

    private void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }
}
