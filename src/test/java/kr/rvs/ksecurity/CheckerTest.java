package kr.rvs.ksecurity;

import kr.rvs.ksecurity.blacklist.Checker;
import kr.rvs.ksecurity.blacklist.Parser;
import kr.rvs.ksecurity.factory.MockFactory;
import org.bukkit.entity.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Junhyeong Lim on 2017-10-24.
 */
public class CheckerTest extends Assert {
    private final UUID randUUID = UUID.randomUUID();
    private Set<Checker> checkers;

    @Before
    public void parse() {
        checkers = Parser.parseBlackList(new StringReader(
                "ip|127.0.0.1\n" +
                        "name|EntryPoint\n" +
                        "uuid|" + randUUID.toString()
        ));
    }

    @Test
    public void test() {
        assertTrue(checkers.size() == 3);

        Player player = MockFactory.createMockPlayer("EntryPoint", randUUID, "127.0.0.1");
        for (Checker checker : checkers) {
            assertTrue(checker.check(player));
        }
    }
}
