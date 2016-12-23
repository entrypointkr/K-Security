package cloud.swiftnode.kspam.abstraction.processer;

import cloud.swiftnode.kspam.abstraction.RunnableProcesser;
import cloud.swiftnode.kspam.storage.StaticStorage;
import cloud.swiftnode.kspam.util.Path;

import java.io.FileOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Created by EntryPoint on 2016-12-23.
 */
public class CacheWriteProcesser extends RunnableProcesser {
    @Override
    public void process() {
        try {
            FileOutputStream stream = new FileOutputStream(Path.CACHE.toFile());
            ObjectOutput output = new ObjectOutputStream(stream);
            output.writeObject(StaticStorage.getCachedIpSet());
            stream.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
