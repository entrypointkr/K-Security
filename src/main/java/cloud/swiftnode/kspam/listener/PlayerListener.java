package cloud.swiftnode.kspam.listener;

import cloud.swiftnode.kspam.abstraction.checker.LocalIpChecker;
import cloud.swiftnode.kspam.abstraction.checker.SpamCacheChecker;
import cloud.swiftnode.kspam.abstraction.checker.SpamHttpChecker;
import cloud.swiftnode.kspam.abstraction.convertor.IpStringConvertor;
import cloud.swiftnode.kspam.abstraction.processer.MCBlacklistProcesser;
import cloud.swiftnode.kspam.abstraction.processer.PunishSpamProcesser;
import cloud.swiftnode.kspam.storage.SpamStorage;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.storage.VersionStorage;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Result;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.Type;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class PlayerListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        Static.removePlayerInStorage(e.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(final PlayerLoginEvent e) {
        // Only ALLOWED
        if (e.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            return;
        }
        // Storage
        final SpamStorage storage = new SpamStorage(Result.ERROR, e.getAddress());
        // Local IP Check
        if (new LocalIpChecker(storage.getIp()).check()) {
            return;
        }
        // ForceMode
        if (StaticStorage.isForceMode() && !e.getPlayer().hasPlayedBefore()) {
            storage.setResult(Result.TRUE);
            storage.setType(Type.FORCE);
            new PunishSpamProcesser(storage, e).process();
            return;
        }
        // Cache check
        if (new SpamCacheChecker(storage).check()) {
            new PunishSpamProcesser(storage, e).process();
            return;
        }
        // Http check
        Static.runTaskAsync(new Runnable() {
            @Override
            public void run() {
                new SpamHttpChecker(storage).check();
                new PunishSpamProcesser(storage, e.getPlayer()).process();
                new MCBlacklistProcesser(e).process();
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(final PlayerJoinEvent e) {
        // TODO : 캐쉬에 닉네임과 UUID 타입 추가
        if (StaticStorage.getCachedIpSet().contains(e.getPlayer().getAddress().getHostName())) {
            e.setJoinMessage(null);
        } else if (StaticStorage.getCachedMCBlacklistSet().contains(e.getPlayer().getName())) {
            e.setJoinMessage(null);
        }

        Player p = e.getPlayer();
        if (!p.isOp()) {
            return;
        }
        VersionStorage storage = StaticStorage.getVersionStorage();
        if (storage == null) {
            return;
        }
        if (storage.isOld()) {
            Static.msgLineLoop(p, Lang.PREFIX.toString() + Lang.NEW_VERSION + "\n" +
                    Lang.VERSION.toString(Lang.PREFIX, storage.getCurrVer().toString(), storage.getNewVer().toString()));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuitHighest(PlayerQuitEvent e) {
        // TODO : 캐쉬에 닉네임과 UUID 타입 추가
        if (StaticStorage.getCachedIpSet().contains(e.getPlayer().getAddress().getHostName())) {
            e.setQuitMessage(null);
        } else if (StaticStorage.getCachedMCBlacklistSet().contains(e.getPlayer().getName())) {
            e.setQuitMessage(null);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPing(ServerListPingEvent e) {
        String ip = new IpStringConvertor(e.getAddress()).convert();
        if (StaticStorage.getCachedIpSet().contains(ip)) {
            e.setMotd(Lang.MOTD.toString());
        }
    }
}
