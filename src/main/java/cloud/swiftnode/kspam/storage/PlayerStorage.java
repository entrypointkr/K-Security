package cloud.swiftnode.kspam.storage;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class PlayerStorage {
    private static PlayerStorage INSTANCE;
    private final Set<String> playerList;

    public PlayerStorage() {
        INSTANCE = this;
        playerList = new HashSet<>();
    }

    public static PlayerStorage getInst() {
        return INSTANCE;
    }

    public Set<String> getPlayerSet() {
        return playerList;
    }
}
