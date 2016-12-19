package cloud.swiftnode.kspam.storage;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class StaticStorage {
    private static Set<String> playerList;
    private static VersionStorage versionStorage;

    public static Set<String> getPlayerSet() {
        if (playerList == null) {
            playerList = new HashSet<>();
        }
        return playerList;
    }

    public static VersionStorage getVersionStorage() {
        return versionStorage;
    }

    public static void setVersionStorage(VersionStorage versionStorage) {
        StaticStorage.versionStorage = versionStorage;
    }
}
