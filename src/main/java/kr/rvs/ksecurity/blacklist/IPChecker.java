package kr.rvs.ksecurity.blacklist;

import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-10-24.
 */
public class IPChecker implements Checker {
    private final String ip;

    public IPChecker(String ip) {
        this.ip = ip;
    }

    @Override
    public boolean check(Player player) {
        return player.getAddress().getAddress().getHostAddress().equals(ip);
    }
}
