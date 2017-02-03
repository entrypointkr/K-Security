package cloud.swiftnode.ksecurity.command;

import cloud.swiftnode.ksecurity.abstraction.convertor.StringToIpConverter;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.SpamExecutor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.executor.BaseSpamExecutor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.executor.DebugSpamExecutor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.processor.AsyncLoginProcessor;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.processor.SyncLoginProcessor;
import cloud.swiftnode.ksecurity.util.Config;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Static;
import cloud.swiftnode.ksecurity.util.StaticStorage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cloud.swiftnode.ksecurity.util.Static.getConfig;

/**
 * Created by Junhyeong Lim on 2017-01-17.
 */
public class Commands implements CommandExecutor {
    private static final Pattern IP_PATTERN;

    static {
        IP_PATTERN = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean isOp = sender.isOp();
        // Lazy
        switch (args.length) {
            case 1:
                if (args[0].equalsIgnoreCase("firewall")) {
                    if (!isOp) {
                        break;
                    }
                    StaticStorage.firewallMode = !StaticStorage.firewallMode;
                    sender.sendMessage(Lang.SET.builder().single(Lang.Key.VALUE, StaticStorage.firewallMode).build());
                    return true;
                } else if (args[0].equalsIgnoreCase("info")) {
                     /*
                     K-SPAM 은 AGPL 라이선스이며 개발자, 플러그인 정보, 소스 제공이 의무입니다.
                     밑 메세지 전송 코드를 제거 시 법적 책임을 물을 수 있습니다.
                     본 프로젝트에 기여했을 경우 밑 메세지에 자신의 닉네임을 추가할 수 있습니다.
                     */
                    sender.sendMessage(Lang.LAW_INFO.builder().build());
                    sender.sendMessage(Lang.NEW_VERSION.builder().single(Lang.Key.NEW_VERSION, StaticStorage.getNewVer()).build());
                    sender.sendMessage(Lang.CURRENT_VERSION.builder().single(Lang.Key.KSPAM_VERSION, StaticStorage.getCurrVer()).build());
                    Static.sendModulesInfo(sender);
                    sender.sendMessage(Lang.PREFIX + String.valueOf(StaticStorage.cachedSet.size()));
                    sender.sendMessage(Lang.PREFIX + String.valueOf(StaticStorage.FIRST_KICK_CACHED_SET.size()));
                    return true;
                } else if (args[0].equalsIgnoreCase("debug")) {
                    if (!isOp) {
                        break;
                    }
                    getConfig().set(Config.DEBUG_MODE, !Config.isDebugMode());
                    sender.sendMessage(Lang.SET.builder().single(Lang.Key.VALUE, Config.isDebugMode()).build());
                    return true;
                } else if (args[0].equalsIgnoreCase("firstkick")) {
                    if (!isOp) {
                        break;
                    }
                    getConfig().set(Config.FIRST_LOGIN_KICK, !Config.isFirstLoginKick());
                    sender.sendMessage(Lang.SET.builder().single(Lang.Key.VALUE, Config.isFirstLoginKick()).build());
                    return true;
                } else if (args[0].equalsIgnoreCase("alert")) {
                    if (!isOp) {
                        break;
                    }
                    getConfig().set(Config.ALERT, !Config.isAlert());
                    sender.sendMessage(Lang.SET.builder().single(Lang.Key.VALUE, Config.isAlert()).build());
                    return true;
                } else if (args[0].equalsIgnoreCase("netalert")) {
                    if (!isOp) {
                        break;
                    }
                    getConfig().set(Config.NETWORK_ALERT, !Config.isNetworkAlert());
                    sender.sendMessage(Lang.SET.builder().single(Lang.Key.VALUE, Config.isNetworkAlert()).build());
                    return true;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (!isOp) {
                        break;
                    }
                    Config.reload();
                    sender.sendMessage(Lang.CONFIG_RELOADED.builder().build());
                    return true;
                } else if (args[0].equalsIgnoreCase("listop")) {
                    if (!isOp) {
                        break;
                    }
                    sender.sendMessage(Lang.OP_LIST.builder().single(Lang.Key.VALUE,
                            StringUtils.join(StaticStorage.ALLOWED_OP_SET, ", ")).build());
                    return true;
                } else if (args[0].equalsIgnoreCase("clear")) {
                    if (!isOp) {
                        break;
                    }
                    StaticStorage.ALLOWED_OP_SET.clear();
                    sender.sendMessage(Lang.SUCCESS.builder().build());
                    return true;
                }
                break;
            case 2:
                if (args[0].equalsIgnoreCase("check")) {
                    if (!isOp) {
                        break;
                    }
                    String info = new InfoFacade(args[1]).get();
                    if (info == null) {
                        sender.sendMessage(Lang.INVALID_IP.builder().build());
                        return true;
                    }
                    sender.sendMessage(Lang.COMMAND_CHECK.builder().single(Lang.Key.VALUE, info).build());
                    final DeniableInfoAdapter adapter = new DeniableInfoAdapter(false, info);
                    final SpamExecutor executor = new DebugSpamExecutor(new BaseSpamExecutor(), sender);
                    new SyncLoginProcessor(executor, adapter).process();
                    Static.runTaskAsync(() -> new AsyncLoginProcessor(executor, adapter).process());
                    return true;
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (!isOp) {
                        break;
                    }
                    String info = new InfoFacade(args[1]).get();
                    if (info == null) {
                        sender.sendMessage(Lang.INVALID_IP.builder().build());
                        return true;
                    }
                    sender.sendMessage(Lang.PREFIX.toString() + StaticStorage.cachedSet.remove(info));
                    return true;
                } else if (args[0].equalsIgnoreCase("addop")) {
                    if (!isOp) {
                        break;
                    }
                    String player = args[1];
                    if (StaticStorage.ALLOWED_OP_SET.add(player)) {
                        sender.sendMessage(Lang.ADD_OP.builder().single(Lang.Key.VALUE, player).build());
                    } else {
                        sender.sendMessage(Lang.FAIL.builder().build());
                    }
                    return true;
                } else if (args[0].equalsIgnoreCase("remop")) {
                    if (!isOp) {
                        break;
                    }
                    String player = args[1];
                    if (StaticStorage.ALLOWED_OP_SET.remove(player)) {
                        sender.sendMessage(Lang.REM_OP.builder().single(Lang.Key.VALUE, player).build());
                    } else {
                        sender.sendMessage(Lang.FAIL.builder().build());
                    }
                    return true;
                }
        }
        sender.sendMessage(Lang.CMD_USAGE.builder().build(false));
        return false;
    }

    class InfoFacade {
        private String target;

        InfoFacade(String target) {
            this.target = target;
        }

        String get() {
            Player player = Bukkit.getPlayer(target);
            if (player != null) {
                return new StringToIpConverter(player.getAddress().getAddress().toString()).convert();
            }
            Matcher matcher = IP_PATTERN.matcher(target);
            if (matcher.find()) {
                return target;
            } else {
                return null;
            }
        }
    }
}
