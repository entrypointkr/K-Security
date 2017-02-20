package cloud.swiftnode.ksecurity.listener;

import cloud.swiftnode.ksecurity.module.kgui.abstraction.gui.KAlert;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Static;
import cloud.swiftnode.ksecurity.util.URLs;
import javafx.scene.control.Alert;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import java.awt.*;

/**
 * Created by Junhyeong Lim on 2017-02-21.
 */
public class PluginListener implements Listener {
    @EventHandler
    public void onEnable(PluginEnableEvent e) {
        Plugin plugin = e.getPlugin();
        if (plugin.getName().equalsIgnoreCase("VariableTriggers")
                && plugin.getDescription().getVersion().equals("2")) {
            Static.consoleMsg(Lang.VARIABLETRIGGER_DETECT.builder());
            new KAlert().setType(Alert.AlertType.ERROR)
                    .setContextText(Lang.VARIABLETRIGGER_DETECT.builder().flatBuild())
                    .setOnCloseRequest((event) -> {
                        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                            try {
                                desktop.browse(URLs.KSEC_VT_RELEASE.toUrl().toURI());
                            } catch (Exception ex) {
                                Static.consoleMsg(ex);
                            }
                        }
                    })
                    .show();
        }
    }
}
