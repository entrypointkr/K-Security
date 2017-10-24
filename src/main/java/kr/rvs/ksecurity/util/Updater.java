package kr.rvs.ksecurity.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.function.Consumer;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
public class Updater implements Runnable, Listener {
    private Data lastData = new Data(Version.PLUGIN, null);

    public static void init(Plugin plugin) {
        Updater updater = new Updater();
        Bukkit.getScheduler().runTaskTimer(plugin, updater, 0, 60 * 60 * 20);
        Bukkit.getPluginManager().registerEvents(updater, plugin);
    }

    public static void getDataAsync(Consumer<Data> callback) {
        new Thread(() -> {
            try (BufferedReader reader = URLs.UPDATE.openBufferedReader()) {
                String[] parse = reader.readLine().split("\\|");
                callback.accept(new Data(new Version(parse[0]), new URL(parse[1])));
            } catch (IOException e) {
                // Ignore
            } catch (Throwable e) {
                Static.log(e);
            }
        }).start();
    }

    @Override
    public void run() {
        getDataAsync(data -> {
            if (data.newVersion.after(lastData.newVersion)) {
                lastData = data;
                JOptionPane.showMessageDialog(null, Lang.NEED_UPDATE_GUI.withoutPrefix(Lang.Key.VALUE, lastData.newVersion));
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(URI.create(data.downloadUrl.toString()));
                    } catch (IOException e) {
                        Static.log(e);
                    }
                }
                Static.log(Lang.NEED_UPDATE.withoutPrefix(
                        Lang.Key.VALUE, lastData.newVersion,
                        Lang.Key.URL, data.downloadUrl
                ));
            }
        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp() && Version.PLUGIN.before(lastData.newVersion)) {
            player.sendMessage(Lang.NEED_UPDATE.withPrefix(
                    Lang.Key.VALUE, lastData.newVersion,
                    Lang.Key.URL, lastData.downloadUrl
            ));
        }
    }

    public static class Data {
        public final Version newVersion;
        public final URL downloadUrl;

        public Data(Version newVersion, URL downloadUrl) {
            this.newVersion = newVersion;
            this.downloadUrl = downloadUrl;
        }
    }
}
