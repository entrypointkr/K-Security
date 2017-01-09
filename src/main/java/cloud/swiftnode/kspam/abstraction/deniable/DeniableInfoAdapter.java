package cloud.swiftnode.kspam.abstraction.deniable;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.Info;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class DeniableInfoAdapter implements Deniable, Info {
    private Deniable deniable;
    private Info info;

    public DeniableInfoAdapter(Deniable deniable, Info info) {
        this.deniable = deniable;
        this.info = info;
    }

    @Override
    public String getName() {
        return info.getName();
    }

    @Override
    public String getIp() {
        return info.getName();
    }

    @Override
    public String getUniqueId() {
        return info.getUniqueId();
    }

    @Override
    public void deny() {
        deniable.deny();
    }
}
