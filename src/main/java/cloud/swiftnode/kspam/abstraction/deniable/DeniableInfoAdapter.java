package cloud.swiftnode.kspam.abstraction.deniable;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.ExecuteDeniable;
import cloud.swiftnode.kspam.abstraction.Info;
import cloud.swiftnode.kspam.abstraction.convertor.ObjectToDeniableConverter;
import cloud.swiftnode.kspam.abstraction.convertor.ObjectToInfoConverter;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class DeniableInfoAdapter extends ExecuteDeniable implements Info {
    private Deniable deniable;
    private Info info;

    public DeniableInfoAdapter(Mode mode, Deniable deniable, Info info) {
        super(mode);
        this.deniable = deniable;
        this.info = info;
    }

    public DeniableInfoAdapter(Mode mode, Object obj) {
        super(mode);
        setObj(obj);
    }

    public void setObj(Object obj) {
        this.deniable = new ObjectToDeniableConverter(obj, mode).convert();
        this.info = new ObjectToInfoConverter(obj).convert();
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    @Override
    public String getName() {
        return info.getName();
    }

    @Override
    public String getIp() {
        return info.getIp();
    }

    @Override
    public String getUniqueId() {
        return info.getUniqueId();
    }

    @Override
    public void executeDeny() {
        deniable.deny();
    }
}
