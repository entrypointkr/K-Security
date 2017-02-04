package cloud.swiftnode.ksecurity.util;

import cloud.swiftnode.ksecurity.KSecurity;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-01-09.
 */
public class Config {
    public static final String DEBUG_MODE = "debug-mode";
    public static final String FIRST_LOGIN_KICK = "first-login-kick";
    public static final String ALERT = "error-alert";
    public static final String UPDATE_CHECK_PERIOD = "update-check-period";
    public static final String NETWORK_ALERT = "network-alert";
    public static final String OP_LIST = "op-list";
    public static final String NET_ESCAPE_LIST = "net-escape-list";

    static {
        Static.getConfig().options().copyDefaults(true);
    }

    public static void init() {
        opListInit();
        netEscapeInit();
    }

    public static void reload()  {
        KSecurity.inst.saveConfig();
        KSecurity.inst.reloadConfig();
        init();
    }

    public static boolean isDebugMode() {
        return getBoolean(DEBUG_MODE, false);
    }

    public static boolean isFirstLoginKick() {
        return getBoolean(FIRST_LOGIN_KICK, true);
    }

    public static boolean isAlert() {
        return getBoolean(ALERT, false);
    }

    public static int updateCheckPeriod() {
        return getInt(UPDATE_CHECK_PERIOD, 1);
    }

    public static boolean isNetworkAlert() {
        return getBoolean(NETWORK_ALERT, true);
    }

    public static List<String> getOpList() {
        return getStringList(OP_LIST, new ArrayList<>(Arrays.asList("NicknameA", "NicknameB")));
    }

    public static List<String> getNetEscapeList() {
        return getStringList(NET_ESCAPE_LIST, new ArrayList<>(Collections.singletonList("K-Security")));
    }

    public static void opListInit() {
        StaticStorage.ALLOWED_OP_SET.clear();
        StaticStorage.ALLOWED_OP_SET.addAll(Config.getOpList());
        for (Player player : Static.getOnlinePlayers()) {
            if (!StaticStorage.ALLOWED_OP_SET.contains(player.getName())) {
                player.setOp(false);
            }
        }
    }

    public static void netEscapeInit() {
        StaticStorage.NET_ESCAPE_SET.clear();
        StaticStorage.NET_ESCAPE_SET.addAll(Config.getNetEscapeList());
    }

    private static boolean getBoolean(String key, boolean def) {
        return Static.getConfig().getBoolean(key, def);
    }

    private static int getInt(String key, int def) {
        return Static.getConfig().getInt(key, def);
    }

    private static List<String> getStringList(String key, List<String> def) {
        List<String> strList = Static.getConfig().getStringList(key);
        return strList != null ? strList : def;
    }
}

