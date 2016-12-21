package cloud.swiftnode.kspam.runnable;

import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.storage.VersionStorage;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.URLs;
import cloud.swiftnode.kspam.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by EntryPoint on 2016-12-21.
 */
public class UpdateBukkicRunnable extends BukkitRunnable {
    private int errorCount;
    private int test = 0;

    @Override
    public synchronized void run() {
        try {
            VersionStorage storage = StaticStorage.getVersionStorage();
            String text = Static.readAllText(URLs.GRADLE.toUrl());
            Version newVer = new Version(Static.substring(text, "version '", "'"));
            Version cachedNewVer = storage.getNewVer();
            Version currVer = StaticStorage.getVersionStorage().getCurrVer();
            // Ensure
            if (cachedNewVer.toString().equals("0.0.0")) {
                storage.setNewVer(currVer);
                cachedNewVer = storage.getNewVer();
            }
            if (newVer.after(cachedNewVer)) {
                Static.msgLineLoop(Bukkit.getConsoleSender(), Lang.PREFIX.toString() + Lang.NEW_VERSION + "\n" +
                        Lang.VERSION.toString(Lang.PREFIX, currVer.toString(), newVer.toString()));
                StaticStorage.setVersionStorage(new VersionStorage(currVer, newVer));
                storage.setNewVer(newVer);
            }
        } catch (Exception ex) {
            if (errorCount >= 4) {
                Static.consoleMsg(
                        Lang.PREFIX + Lang.EXCEPTION.toString("버전 확인 에러, 타이머를 종료합니다." + ex.getMessage()));
                cancel();
            } else {
                errorCount = ++errorCount;
                run();
            }
        }
    }
}
