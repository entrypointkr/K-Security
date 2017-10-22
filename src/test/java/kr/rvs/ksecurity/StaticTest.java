package kr.rvs.ksecurity;

import kr.rvs.ksecurity.antibot.PingAntiBot;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Junhyeong Lim on 2017-10-22.
 */
public class StaticTest extends Assert {
    @Test
    public void hostnameFormatting() {
        String hostNameA = "localhost:25565";
        String hostNameB = "localhost:25564";
        assertEquals(PingAntiBot.formattingHostname(hostNameA), "localhost");
        assertEquals(PingAntiBot.formattingHostname(hostNameB), hostNameB);
    }
}
