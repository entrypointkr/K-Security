package cloud.swiftnode.kspam.abstraction.processer;

import cloud.swiftnode.kspam.abstraction.RunnableProcesser;
import cloud.swiftnode.kspam.storage.StaticStorage;

import java.io.File;

/**
 * Created by EntryPoint on 2016-12-23.
 */
public class DeleteAllDataProcesser extends RunnableProcesser {
    @Override
    public void process() {
        for (File file : StaticStorage.getSpamFileSet()) {
            file.delete();
        }
    }
}
