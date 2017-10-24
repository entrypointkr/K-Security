package kr.rvs.ksecurity.blacklist;

import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-10-24.
 */
public interface Checker {
    boolean check(Player player);
}
