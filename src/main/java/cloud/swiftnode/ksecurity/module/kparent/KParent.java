package cloud.swiftnode.ksecurity.module.kparent;

import cloud.swiftnode.ksecurity.command.Commands;
import cloud.swiftnode.ksecurity.listener.PlayerListener;
import cloud.swiftnode.ksecurity.listener.PluginListener;
import cloud.swiftnode.ksecurity.listener.ServerListener;
import cloud.swiftnode.ksecurity.module.Module;
import cloud.swiftnode.ksecurity.module.kparent.abstraction.processor.MetricsInitProcessor;
import cloud.swiftnode.ksecurity.module.kparent.abstraction.processor.UpdateCheckProcessor;
import cloud.swiftnode.ksecurity.util.Config;
import cloud.swiftnode.ksecurity.util.Lang;
import cloud.swiftnode.ksecurity.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.reader.StreamReader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

/**
 * Created by Junhyeong Lim on 2017-02-03.
 */
public class KParent extends Module {
    static {
        injectNonPrintable();
    }

    private static void injectNonPrintable() {
        try {
            Field field = StreamReader.class.getDeclaredField("NON_PRINTABLE");
            field.setAccessible(true);
            Static.crackFinalStatic(field);
            field.set(null, Pattern.compile("[^\t\n\r\u0020-\u007E\u0085\u00A0-\uD7FF\uE000-\uFFFD]"));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Static.consoleMsg(e);
        }
    }

    public KParent(JavaPlugin parent) {
        super(parent);
    }

    @Override
    public String getSimpleVersion() {
        return "1.0";
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        Config.init(getConfig());
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), parent);
        Bukkit.getPluginManager().registerEvents(new ServerListener(), parent);
        Bukkit.getPluginManager().registerEvents(new PluginListener(), parent);
        new MetricsInitProcessor().process();
        Static.runTaskTimerAsync(new UpdateCheckProcessor()::process, 0, Config.updateCheckPeriod() * 3600 * 20);
        getCommand("ks").setExecutor(new Commands());
        Static.consoleMsg(Lang.INTRO.builder()
                .addKey(Lang.Key.KSEC_VERSION, Lang.Key.MODULES_INFO)
                .addVal(Static.getVersion(), Lang.MODULES_INFO)
                .build(false, 1));
    }

    @Override
    public void onDisable() {
        try {
            Config.getConfig().save(Config.getDataFile());
        } catch (IOException e) {
            Static.consoleMsg(e);
        }
    }

    @Override
    public void onLoad() {
        Config.refresh();
        try {
            new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()).setReadOnly();
        } catch (URISyntaxException e) {
            Static.consoleMsg(e);
        }
    }
}
