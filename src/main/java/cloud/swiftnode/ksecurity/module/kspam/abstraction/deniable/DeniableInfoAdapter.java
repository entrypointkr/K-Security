package cloud.swiftnode.ksecurity.module.kspam.abstraction.deniable;

import cloud.swiftnode.ksecurity.abstraction.convertor.ObjectToDeniableConverter;
import cloud.swiftnode.ksecurity.abstraction.convertor.ObjectToInfoConverter;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.Deniable;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.DeniableInfo;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.ExecuteDeniable;
import cloud.swiftnode.ksecurity.module.kspam.abstraction.Info;
import cloud.swiftnode.ksecurity.util.Lang;
import org.bukkit.entity.Player;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public class DeniableInfoAdapter extends ExecuteDeniable implements DeniableInfo {
    private Deniable deniable;
    private Info info;

    public DeniableInfoAdapter(boolean delayed, Deniable deniable, Info info) {
        super(delayed);
        this.deniable = deniable;
        this.info = info;
    }

    public DeniableInfoAdapter(boolean delayed, Object obj) {
        super(delayed);
        setObj(obj);
    }

    public void setObj(Object obj) {
        this.deniable = new ObjectToDeniableConverter(obj, delayed).convert();
        this.info = new ObjectToInfoConverter(obj).convert();
    }

    @Override
    public void setDelayed(boolean delayed) {
        this.delayed = delayed;
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
    public Player getPlayer() {
        return info.getPlayer();
    }

    @Override
    public void executeDeny() {
        deniable.deny();
    }

    @Override
    public Lang.MessageBuilder getDenyMsg() {
        return deniable.getDenyMsg();
    }

    @Override
    public void setDenyMsg(Lang.MessageBuilder msg) {
        deniable.setDenyMsg(msg);
    }
}
