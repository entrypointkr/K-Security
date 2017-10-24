package kr.rvs.ksecurity.blacklist;

import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Junhyeong Lim on 2017-10-24.
 */
public class CompoundChecker implements Checker {
    private final Set<Checker> checkers = new HashSet<>();

    public CompoundChecker(Collection<Checker> checkers) {
        this.checkers.addAll(checkers);
    }

    public CompoundChecker add(Checker... checkers) {
        this.checkers.addAll(Arrays.asList(checkers));
        return this;
    }

    @Override
    public boolean check(Player player) {
        for (Checker checker : checkers) {
            if (checker.check(player)) {
                return true;
            }
        }
        return false;
    }

    public Set<Checker> getCheckers() {
        return new HashSet<>(checkers);
    }
}
