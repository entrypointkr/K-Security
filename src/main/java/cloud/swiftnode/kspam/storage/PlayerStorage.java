package cloud.swiftnode.kspam.storage;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class PlayerStorage {
    private static Set<String> playerList = new HashSet<>();

    public static Set<String> getPlayerSet() {
        return playerList;
    }
}
