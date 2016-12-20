package cloud.swiftnode.kspam.storage;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class StaticStorage {
    private static Set<String> playerList;
    private static VersionStorage versionStorage;
    private static Set<String> ipList;
    private static boolean errorMessage;

    // TODO: Synchronized ?

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

    public static Set<String> getCachedIpSet() {
        if (ipList == null) {
            ipList = new HashSet<>();
        }
        return ipList;
    }

    public static boolean isErrorMessage() {
        return errorMessage;
    }

    public static void setErrorMessage(boolean errorMessage) {
        StaticStorage.errorMessage = errorMessage;
    }
}
