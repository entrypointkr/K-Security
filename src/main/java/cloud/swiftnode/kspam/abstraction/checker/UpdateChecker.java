package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.KSpam;
import cloud.swiftnode.kspam.abstraction.RunnableChecker;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.storage.VersionStorage;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.URLs;
import cloud.swiftnode.kspam.util.Version;
import org.bukkit.Bukkit;

/**
 * Created by EntryPoint on 2016-12-20.
 */
public class UpdateChecker extends RunnableChecker {
    @Override
    public boolean check() {
        try {
            VersionStorage storage = StaticStorage.getVersionStorage();
            if (storage == null) {
                String text = Static.readAllText(URLs.GRADLE.toUrl());
                if (text != null) {
                    Version newVer = new Version(Static.substring(text, "version '", "'"));
                    Version currVer = new Version(KSpam.getInst().getDescription().getVersion());
                    StaticStorage.setVersionStorage(new VersionStorage(newVer, currVer));
                } else {
                    Static.consoleMsg(Lang.PREFIX + Lang.EXCEPTION.toString("버전 확인 에러"));
                }
            }

            String text = Static.readAllText(URLs.GRADLE.toUrl());
            if (text != null) {
                Version newVer = new Version(Static.substring(text, "version '", "'"));
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
        return true;
    }
}
