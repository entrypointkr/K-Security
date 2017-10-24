package kr.rvs.ksecurity.blacklist;

import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-10-24.
 */
public class NameChecker implements Checker {
    private final String name;

    public NameChecker(String name) {
        this.name = name;
    }

    @Override
    public boolean check(Player player) {
        return player.getName().equalsIgnoreCase(name);
    }
}
