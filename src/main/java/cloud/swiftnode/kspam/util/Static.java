package cloud.swiftnode.kspam.util;

import java.net.InetAddress;
import java.net.URL;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class Static {
    public static URL getUrl(String ip) {
        URL url = null;
        try {
            url = new URL("http://kspam.swiftnode.cloud/mcbanip/community.php?ip=" + ip);
        } catch (Exception ex) {
            // Ignore
        }
        return url;
    }

    public static String convertToIp(InetAddress addr) {
        return addr.toString().replace("/", "");
    }
}
