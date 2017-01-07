package cloud.swiftnode.kspam.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by EntryPoint on 2017-01-04.
 */
public class StaticStorage {
    public static Set<String> cachedSet = new HashSet<>();
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
        return newVer;
    }

    public static void setNewVer(Version newVer) {
        StaticStorage.newVer = newVer;
    }
}
