package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.convertor.MCBlacklistResultConvertor;
import cloud.swiftnode.kspam.storage.SpamStorage;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.util.Result;
import cloud.swiftnode.kspam.util.Type;
import cloud.swiftnode.kspam.util.URLs;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by horyu on 2016-12-23.
 */
public class SpamMCBlacklistChecker extends SpamChecker {
    public SpamMCBlacklistChecker(SpamStorage storage) {
        super(storage);
    }

    @Override
    public boolean check() {
        if (storage.getResult() == Result.TRUE) {
            return true;
        }

        try {
            String url = URLs.MCBLACKLIST_API.toString(storage.getIp());
            String text = getJSONText(url);
            storage.setResult(new MCBlacklistResultConvertor(text).convert());
            storage.setType(Type.MCBLACKLIST);
        } catch (Exception ex) {
            // Ignore
        }

        return false;
    }

    private String getJSONText(String urlStr) {
        try {
            URLConnection url = new URL(urlStr).openConnection();
            url.setRequestProperty("User-Agent", "K-SPAM v" + StaticStorage.getVersionStorage().getCurrVer());

            StringBuilder sb = new StringBuilder();
            InputStream in = url.getInputStream();

            byte[] data = new byte[1024];
            int size;
            while ((size = in.read(data)) != -1) {
                sb.append(new String(data, 0, size));
            }

            return sb.toString();
        } catch (Exception ex) {
            // Ignore
        }
        return null;
    }
}