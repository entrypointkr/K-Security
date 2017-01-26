package cloud.swiftnode.kvaccine.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

/**
 * Created by EntryPoint on 2017-01-05.
 */
public enum URLs {
    COMMUNITY_API("http://kvaccine.swiftnode.cloud/mcbanip/community.php?ip={0}"),
    SHROOMERY_API("http://www.shroomery.org/ythan/proxycheck.php?ip={0}"),
    STOPFORUM_API("http://www.stopforumspam.com/api?ip={0}"),
    BOTSCOUT_API("http://www.botscout.com/test/?ip={0}"),
    MCBLACKLIST_API("http://api.mc-blacklist.kr/API/check/{0}"),
    MOJANG_UUID_API("https://api.mojang.com/users/profiles/minecraft/{0}?at={1}"),
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
        String addr = MessageFormat.format(this.addr, value);
        return new URL(addr);
    }
}
