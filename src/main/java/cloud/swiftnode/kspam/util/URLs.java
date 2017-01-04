package cloud.swiftnode.kspam.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

/**
 * Created by EntryPoint on 2017-01-05.
 */
public enum URLs {
    COMMUNITY_API("http://kspam.swiftnode.cloud/mcbanip/community.php?ip={0}"),
    SHROOMERY_API("http://www.shroomery.org/ythan/proxycheck.php?ip={0}"),
    STOPFORUM_API("http://www.stopforumspam.com/api?ip={0}"),
    BOTSCOUT_API("http://www.botscout.com/test/?ip={0}"),
    MCBLACKLIST_API("http://api.mc-blacklist.kr/API/ip/{0}"),
    GRADLE("https://raw.githubusercontent.com/EntryPointKR/K-SPAM/master/build.gradle"),
    CACHE("https://github.com/EntryPointKR/K-SPAM/blob/master/K-Spam.cache?raw=true");
    private final String addr;

    URLs(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return addr;
    }

    public URL toUrl() throws MalformedURLException {
        return toUrl("");
    }

    public URL toUrl(Object... value) throws MalformedURLException {
        String addr = MessageFormat.format(COMMUNITY_API.toString(), value);
        return new URL(addr);
    }
}
