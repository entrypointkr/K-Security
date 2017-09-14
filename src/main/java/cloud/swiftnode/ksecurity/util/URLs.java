package cloud.swiftnode.ksecurity.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

/**
 * Created by EntryPoint on 2017-01-05.
 */
public enum URLs {
    STOPFORUM_API("http://www.stopforumspam.com/api?ip={0}"),
    MCBLACKLIST_API("http://api.mc-blacklist.kr/API/check/{0}"),
    MOJANG_UUID_API("https://api.mojang.com/users/profiles/minecraft/{0}?at={1}"),
    CACHE("https://github.com/EntryPointKR/K-Security/blob/master/K-Spam.cache?raw=true"),
    KSEC_RELEASE("https://github.com/EntryPointKR/K-Security/releases/latest"),
    KSEC_VT_RELEASE("https://github.com/EntryPointKR/VariableTriggers-for-KSecurity/releases"),
    KSEC_SK_RELEASE("https://github.com/EntryPointKR/Skript-for-KSecurity/releases/latest"),
    ;
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
