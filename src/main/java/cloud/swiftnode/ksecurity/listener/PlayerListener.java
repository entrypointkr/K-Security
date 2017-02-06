package cloud.swiftnode.ksecurity.listener;

import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.processor.AsyncLoginProcessor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.processor.SyncLoginProcessor;
import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.intercepter.KPermissible;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Reflections;
import cloud.swiftnode.ksecurity.util.Static;
import cloud.swiftnode.ksecurity.util.StaticStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.permissions.PermissibleBase;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class PlayerListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onLoginSpam(PlayerLoginEvent e) {
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

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLoginInjection(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        try {
            Class superCls = player.getClass().getSuperclass();
            Object obj = Reflections.getDecFieldObj(superCls, player, "perm");
            if (!(obj instanceof PermissibleBase)) {
                throw new Exception("Unexpected type perm: " + obj.getClass().getTypeName());
            }
            PermissibleBase def = (PermissibleBase) obj;
            @SuppressWarnings("ConstantConditions")
            KPermissible permissible = new KPermissible(def, player);
            Reflections.setDecField(superCls, player, "perm", permissible);
        } catch (Exception ex) {
            Static.consoleMsg(ex);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoinHighest(PlayerJoinEvent e) {
        /*
        K-SPAM 은 AGPL 라이선스이며 개발자, 플러그인 정보, 소스 제공이 의무입니다.
        밑 메세지 전송 코드를 제거 시 법적 책임을 물을 수 있습니다.
        */
        Player player = e.getPlayer();
        player.sendMessage(Lang.PLUGIN_INTRO.builder().build(false));

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

    @EventHandler
    public void onJoinOpCheck(PlayerJoinEvent e) {
        Static.runTask(() -> {
            System.out.println(e);
            if (!Static.checkOpable(e.getPlayer())) {
                e.getPlayer().setOp(false);
                Bukkit.broadcastMessage(Lang.DEOP.builder()
                        .single(Lang.Key.VALUE, e.getPlayer().getName())
                        .build());
            }
        });
    }
}
