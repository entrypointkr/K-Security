package cloud.swiftnode.kspam.abstraction;

import java.io.File;

/**
 * Created by EntryPoint on 2016-12-23.
 */
public abstract class FileConverter implements Convertor<File> {
    protected Object obj;

    public FileConverter(Object obj) {
        this.obj = obj;
    }
}
