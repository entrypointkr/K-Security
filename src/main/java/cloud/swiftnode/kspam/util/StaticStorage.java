package cloud.swiftnode.kspam.util;


import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class StaticStorage {
    public static Set<String> cachedSet = new LinkedHashSet<>();
    public static Set<String> firstKickCachedSet = new LinkedHashSet<>();
    private static Map<ClassLoader, Plugin> cachedLoaderPluginMap;
    public static boolean firewallMode = false;
    private static Version currVer;
    private static Version newVer;

    public static Version getCurrVer() {
        if (currVer == null) {
            currVer = new Version(Static.getVersion());
        }
        return currVer;
    }

    public static void setCurrVer(Version currVer) {
        StaticStorage.currVer = currVer;
    }

    public static Version getNewVer() {
        if (newVer == null) {
            newVer = new Version("0.0");
        }
        return newVer;
    }

    public static void setNewVer(Version newVer) {
        StaticStorage.newVer = newVer;
    }

    public synchronized static Map<ClassLoader, Plugin> getCachedLoaderPluginMap() {
        if (cachedLoaderPluginMap == null) {
            cachedLoaderPluginMap = new HashMap<>();
            for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                StaticStorage.cachedLoaderPluginMap.put(plugin.getClass().getClassLoader(), plugin);
            }
        }
        return cachedLoaderPluginMap;
    }
}
