package kr.rvs.ksecurity.blacklist;

import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Junhyeong Lim on 2017-10-24.
 */
public class UUIDChecker implements Checker {
    private final UUID uuid;

    public UUIDChecker(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean check(Player player) {
        return player.getUniqueId().equals(uuid);
    }
}
