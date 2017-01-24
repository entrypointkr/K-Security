package cloud.swiftnode.kspam.listener;

import cloud.swiftnode.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.abstraction.processor.AsyncLoginProcessor;
import cloud.swiftnode.kspam.abstraction.processor.SyncLoginProcessor;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.StaticStorage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class PlayerListener implements Listener {
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
            Static.runTaskLaterAsync(new Runnable() {
                @Override
                public void run() {
                    if (player.isOnline()) {
                        new AsyncLoginProcessor(executor, adapter).process();
                    }
                }
            }, 20L);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoinHighest(PlayerJoinEvent e) {
        /*
        K-SPAM 은 AGPL 라이선스이며 개발자, 플러그인 정보, 소스 제공이 의무입니다.
        밑 메세지 전송 코드를 제거 시 법적 책임을 물을 수 있습니다.
        */
        Player player = e.getPlayer();
        player.sendMessage(Lang.LAW_INFO.builder().build());

        if (player.isOp() && StaticStorage.getNewVer().after(StaticStorage.getCurrVer())) {
            player.sendMessage(Lang.UPDATE_INFO_NEW.builder().prefix().build());
            player.sendMessage(Lang.NEW_VERSION.builder()
                    .single(Lang.Key.NEW_VERSION, StaticStorage.getNewVer())
                    .prefix().build());
            player.sendMessage(Lang.CURRENT_VERSION.builder()
                    .single(Lang.Key.KSPAM_VERSION, StaticStorage.getCurrVer())
                    .prefix().build());
            player.sendMessage(Lang.DOWNLOAD_URL.builder().prefix().build());
        }
    }
}
