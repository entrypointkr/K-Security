package cloud.swiftnode.kspam.storage;

import cloud.swiftnode.kspam.KSpam;
import cloud.swiftnode.kspam.util.Version;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class StaticStorage {
    private static Set<String> playerList;
    private static VersionStorage versionStorage;
    private static Set<String> cachedIpSet;
    private static Set<Player> cachedMCBlacklistSet;
    private static boolean errorMessage = true;
    private static boolean forceMode = false;

    // TODO: Synchronized ?

    public static Set<String> getPlayerSet() {
        if (playerList == null) {
            playerList = new HashSet<>();
        }
        return playerList;
    }

    public static VersionStorage getVersionStorage() {
        if (versionStorage == null) {
            versionStorage = new VersionStorage(
                    new Version(KSpam.getInst().getDescription().getVersion()),
                    new Version(""));
        }
        return versionStorage;
    }

    public static void setVersionStorage(VersionStorage versionStorage) {
        StaticStorage.versionStorage = versionStorage;
    }

    public static Set<String> getCachedIpSet() {
        if (cachedIpSet == null) {
            cachedIpSet = new HashSet<>();
        }
        return cachedIpSet;
    }

    public static Set<Player> getCachedMCBlacklistSet() {
        if (cachedMCBlacklistSet == null) {
            cachedMCBlacklistSet = new HashSet<>();
        }
        return cachedMCBlacklistSet;
}

    public static void setCachedIpSet(Set<String> cachedIpSet) {
        StaticStorage.cachedIpSet = cachedIpSet;
    }

    public static void setCachedMCBlacklistSet(Set<Player> cachedMCBlacklistSet) {
        StaticStorage.cachedMCBlacklistSet = cachedMCBlacklistSet;
    }

    public static boolean isErrorMessage() {
        return errorMessage;
    }

    public static void setErrorMessage(boolean errorMessage) {
        StaticStorage.errorMessage = errorMessage;
    }

    public static boolean isForceMode() {
        return forceMode;
    }

    public static void setForceMode(boolean forceMode) {
        StaticStorage.forceMode = forceMode;
    }
}
