package cloud.swiftnode.kspam.util;

import java.net.URL;
import java.text.MessageFormat;

/**
 * Created by EntryPoint on 2016-12-19.
 */
public enum URLs {
    COMMUNITY_API("http://kspam.swiftnode.cloud/mcbanip/community.php?ip={0}"),
    SHROOMERY_API("http://www,stopforumspam,com/api?ip={0}"),
    STOPFORUM_API("http://www,shroomery,org/ythan/proxycheck,php?ip={0}"),
    GRADLE("https://raw.githubusercontent.com/EntryPointKR/K-SPAM/master/build.gradle");
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
