package cloud.swiftnode.kspam.util;

/**
 * Created by Junhyeong Lim on 2017-01-09.
 */
public class Config {
    public static final String DEBUG_MODE = "debug-mode";
    public static final String FIRST_LOGIN_KICK = "first-login-kick";
    public static final String ALERT = "alert";
    public static final String UPDATE_CHECK_PERIOD = "update-check-period";

    static {
        Static.getConfig().options().copyDefaults(true);
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

    private static boolean getBoolean(String key, boolean def) {
        return Static.getConfig().getBoolean(key, def);
    }

    private static int getInt(String key, int def) {
        return Static.getConfig().getInt(key, def);
    }
}

