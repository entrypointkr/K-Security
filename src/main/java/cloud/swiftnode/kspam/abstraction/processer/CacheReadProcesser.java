package cloud.swiftnode.kspam.abstraction.processer;

import cloud.swiftnode.kspam.KSpam;
import cloud.swiftnode.kspam.abstraction.RunnableProcesser;
import cloud.swiftnode.kspam.runnable.DownloadBukkitRunnable;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Path;
import cloud.swiftnode.kspam.util.Static;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Set;

/**
 * Created by EntryPoint on 2016-12-23.
 */
public class CacheReadProcesser extends RunnableProcesser {
    @Override
    @SuppressWarnings("unchecked")
    public void process() {
        File file = Path.CACHE.toFile();
        if (file.isFile()) {
            try {
                FileInputStream stream = new FileInputStream(file);
                ObjectInput input = new ObjectInputStream(stream);
                StaticStorage.setCachedIpSet((Set<String>) input.readObject());
                Static.consoleMsg(Lang.PREFIX + Lang.CACHE_COUNT.toString(StaticStorage.getCachedIpSet().size()));
            } catch (Exception ex) {
                ex.printStackTrace();
                Static.consoleMsg(Lang.PREFIX + Lang.EXCEPTION.toString());
                if (!file.renameTo(Path.CACHE_BAK.toFile())) {
                    file.delete();
                }
            }
        } else {
            new DownloadBukkitRunnable().runTask(KSpam.getInst());
        }
    }
}
