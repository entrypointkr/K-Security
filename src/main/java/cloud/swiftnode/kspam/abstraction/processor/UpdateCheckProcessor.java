package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.abstraction.Processor;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.StaticStorage;
import cloud.swiftnode.kspam.util.Version;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class UpdateCheckProcessor implements Processor {
    @Override
    public boolean process() {
        URL url;
        try {
            url = new URL("https://github.com/EntryPointKR/K-SPAM/releases/latest");
        } catch (MalformedURLException ex) {
            Static.consoleMsg(ex);
            return false;
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("<span class=\"css-truncate-target\">")) {
                    continue;
                }
                StaticStorage.setNewVer(new Version(
                        Static.substring(line, "<span class=\"css-truncate-target\">", "</span>")));
                if (StaticStorage.getCurrVer().before(StaticStorage.getNewVer())) {
                    Static.consoleMsg(
                            Lang.UPDATE_INFO_NEW.builder(),
                            Lang.NEW_VERSION.builder().single(Lang.Key.NEW_VERSION, StaticStorage.getNewVer()),
                            Lang.CURRENT_VERSION.builder().single(Lang.Key.KSPAM_VERSION, StaticStorage.getCurrVer()),
                            Lang.DOWNLOAD_URL.builder()
                    );
                }
            }
        } catch (Exception ex) {
            Static.consoleMsg(ex);
            return false;
        }
        return true;
    }
}
