package cloud.swiftnode.kspam.storage;

import cloud.swiftnode.kspam.util.Version;

/**
 * Created by EntryPoint on 2016-12-20.
 */
public class VersionStorage {
    private Version currVer;
    private Version newVer;

    public VersionStorage(Version currVer, Version newVer) {
        this.currVer = currVer;
        this.newVer = newVer;
    }

    public Version getCurrVer() {
        return currVer;
    }

    public Version getNewVer() {
        return newVer;
    }
}

