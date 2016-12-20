package cloud.swiftnode.kspam.runnable;

import cloud.swiftnode.kspam.KSpam;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.storage.VersionStorage;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.URLs;
import cloud.swiftnode.kspam.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by EntryPoint on 2016-12-20.
 */
public class UpdateCheckRunnable extends BukkitRunnable {
    @Override
    public void run() {
        try {
            String text = Static.readAllText(URLs.GRADLE.toUrl());
            if (text != null) {
                String verKeyword = "version '";
                String tempNewVer = text.substring(text.indexOf(verKeyword) + verKeyword.length());
                Version newVer = new Version(tempNewVer.substring(0, tempNewVer.indexOf("'")));
                Version currVer = new Version(KSpam.getInst().getDescription().getVersion());
                if (newVer.beforeEquals(currVer)) {
                    Static.consoleMsg(Lang.PREFIX + Lang.LAST_VERSION.toString());
                } else {
                    Static.msgLineLoop(Bukkit.getConsoleSender(), Lang.PREFIX.toString() + Lang.NEW_VERSION + "\n" +
                            Lang.VERSION.toString(Lang.PREFIX, currVer.toString(), newVer.toString()));
                    StaticStorage.setVersionStorage(new VersionStorage(currVer, newVer));
                }
            } else {
                throw new Exception("null");
            }
        } catch (Exception ex) {
            Static.consoleMsg(
                    Lang.PREFIX + Lang.EXCEPTION.toString("버전 확인 에러 " + ex.getMessage()));
        }
    }
}
