package kr.rvs.ksecurity.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by Junhyeong Lim on 2017-10-24.
 */
public enum URLs {
    UPDATE("https://gist.github.com/EntryPointKR/41a19fc38fe34441947411cff9489771/raw"),
    IP_CHECKER("http://checkip.amazonaws.com"),
    BLACKLIST("https://gist.github.com/EntryPointKR/cd8efbcc523cbbfcf141b57dfe0c43eb/raw"),
    SPIGOT_152_LATEST("https://yivesmirror.com/grab/spigot/spigot-1.5.2-R1.1-SNAPSHOT.jar"),
    ;
    private final String url;

    URLs(String url) {
        this.url = url;
    }

    public URL toURL() {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalStateException(e);
        }
    }

    public URI toURI() {
        return URI.create(url);
    }

    public InputStream openStream() throws IOException {
        return Static.openStream(toURL());
    }

    public Reader openReader() {
        return Static.openReader(toURL());
    }

    public BufferedReader openBufferedReader() {
        return new BufferedReader(openReader());
    }
}
