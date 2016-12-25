package cloud.swiftnode.kspam.util;

import java.net.URL;
import java.text.MessageFormat;

/**
 * Created by EntryPoint on 2016-12-19.
 */
public enum URLs {
    COMMUNITY_API("http://kspam.swiftnode.cloud/mcbanip/community.php?ip={0}"),
    SHROOMERY_API("http://www.shroomery.org/ythan/proxycheck.php?ip={0}"),
    STOPFORUM_API("http://www.stopforumspam.com/api?ip={0}"),
    BOTSCOUT_API("http://www.botscout.com/test/?ip={0}"),
    MCBLACKLIST_API("http://api.mc-blacklist.kr/API/check/{0}"),
    MOJANGUUID_API("https://api.mojang.com/users/profiles/minecraft/{0}?at={1}"),
    GRADLE("https://raw.githubusercontent.com/EntryPointKR/K-SPAM/master/build.gradle"),
    CACHE("https://github.com/EntryPointKR/K-SPAM/blob/master/K-Spam.cache?raw=true");
    private final String url;

    URLs(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return toString("");
    }

    public String toString(Object... value) {
        return MessageFormat.format(url, value);
    }

    public URL toUrl() {
        return toUrl("");
    }

    public URL toUrl(Object... value) {
        return Static.toUrl(toString(value));
    }
}
