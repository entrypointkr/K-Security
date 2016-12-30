package cloud.swiftnode.kspam.abstraction.storage;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.Storage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class SpamStorage extends Storage<Deniable> {
    protected List<String> info;

    public SpamStorage(Deniable value) {
        super(value);
        this.info = new ArrayList<>();
    }

    public List<String> getInfo() {
        return info;
    }

    public void setInfo(List<String> info) {
        this.info = info;
    }
}
