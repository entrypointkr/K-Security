package cloud.swiftnode.ksecurity.util;

import cloud.swiftnode.ksecurity.KSecurity;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConstructor;
import org.bukkit.configuration.file.YamlRepresenter;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Junhyeong Lim on 2017-01-09.
 */
public class Config {
    private static final YamlConfiguration config = new YamlConfiguration();

    public static final String DEBUG_MODE = "debug-mode";
    public static final String FIRST_LOGIN_KICK = "first-login-kick";
    public static final String ALERT = "error-alert";
    public static final String UPDATE_CHECK_PERIOD = "update-check-period";
    public static final String NETWORK_ALERT = "network-alert";
    public static final String OP_LIST = "op-list";
    public static final String NET_ESCAPE_LIST = "net-escape-list";

    public static final String ANTICHEAT_DOT = "anticheat.";
    public static final String SHOPKEEPER = ANTICHEAT_DOT + "shopkeeper";
    public static final String CRASH_COLOR = ANTICHEAT_DOT + "crash-color";
    public static final String VARIABLE_TRIGGER = ANTICHEAT_DOT + "variable-trigger";
    public static final String RPGITEM = ANTICHEAT_DOT + "rpgitem";
    public static final String FREECAM = ANTICHEAT_DOT + "freecam";
    public static final String PLAYER_VAULT = ANTICHEAT_DOT + "player-vault";
    public static final String AC_ALERT = ANTICHEAT_DOT + "alert";

    static {
        try {
            Reflections.setDecField(config, "yaml",
                    new Yaml(new YamlConstructor(), new YamlRepresenter(), new DumperOptions()));
            config.load(getDataFile());
        } catch (Exception e) {
            Static.consoleMsg(e);
        }
        getConfig().options().copyDefaults(true);
    }

    public static YamlConfiguration getConfig() {
        return config;
    }

    public static File getDataFile() {
        return new File(Static.getDataFolder(), "config.yml");
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

    public static boolean addNameInOpList(String name) {
        boolean ret;
        List<String> list = getOpList();
        ret = list.add(name);
        setOpList(list);
        return ret;
    }

    public static boolean removeNameInOpList(String name) {
        boolean ret;
        List<String> list = getOpList();
        ret = list.remove(name.toLowerCase());
        setOpList(list);
        return ret;
    }

    public static void setOpList(List<String> strList) {
        List<String> lowerList = new ArrayList<>();
        for (String str : strList) {
            lowerList.add(str.toLowerCase());
        }
        Static.getConfig().set(OP_LIST, lowerList);
    }

    public static List<String> getNetEscapeList() {
        return getStringList(NET_ESCAPE_LIST, new ArrayList<>(Collections.singletonList("K-Security")));
    }

    public static void opListInit() {
        for (Player player : Static.getOnlinePlayers()) {
            if (!getOpList().contains(player.getName())) {
                player.setOp(false);
            }
        }
    }

    public static void netEscapeInit() {
        StaticStorage.NET_ESCAPE_SET.clear();
        StaticStorage.NET_ESCAPE_SET.addAll(Config.getNetEscapeList());
    }

    public static boolean isShopkeeper() {
        return getBoolean(SHOPKEEPER, true);
    }

    public static boolean isCrashColor() {
        return getBoolean(CRASH_COLOR, true);
    }

    public static boolean isVariableTrigger() {
        return getBoolean(VARIABLE_TRIGGER, true);
    }

    public static boolean isRpgItem() {
        return getBoolean(RPGITEM, true);
    }

    public static boolean isFreeCam() {
        return getBoolean(FREECAM, true);
    }

    public static boolean isPlayerVault() {
        return getBoolean(PLAYER_VAULT, true);
    }

    public static boolean isAcAlert() {
        return getBoolean(AC_ALERT, true);
    }

    public static boolean getBoolean(String key, boolean def) {
        return Static.getConfig().getBoolean(key, def);
    }

    public static int getInt(String key, int def) {
        return Static.getConfig().getInt(key, def);
    }

    public static List<String> getStringList(String key, List<String> def) {
        List<String> strList = Static.getConfig().getStringList(key);
        return strList != null ? strList : def;
    }
}

