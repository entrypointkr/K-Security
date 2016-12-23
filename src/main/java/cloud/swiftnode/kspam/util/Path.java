package cloud.swiftnode.kspam.util;

import cloud.swiftnode.kspam.KSpam;

import java.io.File;

/**
 * Created by EntryPoint on 2016-12-23.
 */
public enum Path {
    CACHE(new File(KSpam.getInst().getDataFolder(), "K-Spam.cache")),
    CACHE_BAK(new File(KSpam.getInst().getDataFolder(), "K-Spam.bak"));

    private final File path;

    Path(File path) {
        this.path = path;
    }

    public File toFile() {
        return path;
    }
}
