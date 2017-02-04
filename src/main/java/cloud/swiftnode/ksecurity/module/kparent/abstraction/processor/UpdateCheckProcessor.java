package cloud.swiftnode.ksecurity.module.kparent.abstraction.processor;

import cloud.swiftnode.ksecurity.abstraction.Processor;
import cloud.swiftnode.ksecurity.module.kgui.abstraction.KAlert;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Static;
import cloud.swiftnode.ksecurity.util.StaticStorage;
import cloud.swiftnode.ksecurity.util.URLs;
import cloud.swiftnode.ksecurity.util.Version;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class UpdateCheckProcessor implements Processor {
    @Override
    public boolean process() {
        URL url;
        try {
            url = URLs.KSEC_RELEASE.toUrl();
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
                    Lang.MessageBuilder updateNew = Lang.UPDATE_INFO_NEW.builder();
                    Lang.MessageBuilder newVer = Lang.NEW_VERSION.builder().single(Lang.Key.NEW_VERSION, StaticStorage.getNewVer());
                    Lang.MessageBuilder currVer = Lang.CURRENT_VERSION.builder().single(Lang.Key.KSEC_VERSION, StaticStorage.getCurrVer());
                    Lang.MessageBuilder downURL = Lang.DOWNLOAD_URL.builder();

                    Static.consoleMsg(updateNew, newVer, currVer, downURL);

                    new KAlert().setContextText(
                            updateNew.flatBuild(),
                            newVer.flatBuild(),
                            currVer.flatBuild(),
                            downURL.flatBuild()
                    ).setOnCloseRequest((event) -> {
                        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                            try {
                                desktop.browse(URLs.KSEC_RELEASE.toUrl().toURI());
                            } catch (Exception ex) {
                                Static.consoleMsg(ex);
                            }
                        }
                    }).show();

                }
            }
        } catch (Exception ex) {
            Static.consoleMsg(ex);
            return false;
        }
        return true;
    }
}
