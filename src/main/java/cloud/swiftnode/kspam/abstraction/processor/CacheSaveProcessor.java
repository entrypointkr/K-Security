package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.abstraction.Processor;
import temp.cloud.swiftnode.kspam.KSpam;
import temp.cloud.swiftnode.kspam.util.Static;
import temp.cloud.swiftnode.kspam.util.StaticStorage;

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
            File file = new File(KSpam.INSTANCE.getDataFolder(), "K-Spam.cache");
            ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(file));
            outStream.writeObject(StaticStorage.cachedSet);
            return true;
        } catch (Exception ex) {
            Static.consoleMsg(ex);
            return false;
        }
    }
}
