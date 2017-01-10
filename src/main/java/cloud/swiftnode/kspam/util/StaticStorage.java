package cloud.swiftnode.kspam.util;


import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class StaticStorage {
    public static Set<String> cachedSet = new LinkedHashSet<>();
    public static boolean forceMode = false;
    private static Version currVer;
    private static Version newVer;

    public static Version getCurrVer() {
        if (currVer == null) {
            currVer = new Version(temp.cloud.swiftnode.kspam.util.Static.getVersion());
        }
        return currVer;
    }

    public static void setCurrVer(Version currVer) {
        StaticStorage.currVer = currVer;
    }

    public static Version getNewVer() {
        return newVer;
    }

    public static void setNewVer(Version newVer) {
        StaticStorage.newVer = newVer;
    }
}
