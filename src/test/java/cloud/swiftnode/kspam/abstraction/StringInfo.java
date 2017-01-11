package cloud.swiftnode.kspam.abstraction;

import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class StringInfo implements Info {
    private String ip;
    private String uuid;
    private String name;

    public StringInfo(String ip, String uuid, String name) {
        this.ip = ip;
        this.uuid = uuid;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIp() {
        return ip;
    }

    @Override
    public String getUniqueId() throws IllegalStateException {
        return uuid;
    }

    @Override
    public Player getPlayer() {
        return null;
    }
}
