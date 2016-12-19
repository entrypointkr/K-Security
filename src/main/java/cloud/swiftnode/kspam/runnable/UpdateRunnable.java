package cloud.swiftnode.kspam.runnable;

import cloud.swiftnode.kspam.KSpam;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.URLs;

/**
 * Created by EntryPoint on 2016-12-19.
 */
public class UpdateRunnable implements Runnable {
    @Override
    public void run() {
        try {
            String text = Static.readAllText(URLs.GRADLE.toUrl());
            if (text != null) {
                String newVerStr = text.substring(text.indexOf("version '") + "version '".length());
                newVerStr = newVerStr.substring(0, newVerStr.indexOf("'"));
                String currVerStr = KSpam.getInst().getDescription().getVersion();
                int newVer = Static.toIntVer(newVerStr);
                int currVer = Static.toIntVer(currVerStr);
                if (currVer >= newVer) {
                    Static.consoleMsg(Lang.PREFIX + Lang.LAST_VERSION.toString());
                } else {
                    String msg = Lang.PREFIX.toString() + Lang.NEW_VERSION + "\n" +
                            Lang.VERSION.toString(Lang.PREFIX, currVerStr, newVerStr);
                    for (String element : msg.split("\n")) {
                        Static.consoleMsg(element);
                    }
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
