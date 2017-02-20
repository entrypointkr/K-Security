package cloud.swiftnode.ksecurity.module.kspam.abstraction.processor;

import cloud.swiftnode.ksecurity.abstraction.Processor;
import cloud.swiftnode.ksecurity.util.Static;
import cloud.swiftnode.ksecurity.util.StaticStorage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class CacheSaveProcessor implements Processor {
    @Override
    public boolean process() {
        try {
            File file = new File(Static.getDataFolder(), "K-Spam.cache");
            ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(file));
            outStream.writeObject(StaticStorage.cachedSet);
            return true;
        } catch (Exception ex) {
            Static.consoleMsg(ex);
            return false;
        }
    }
}
