package cloud.swiftnode.kvaccine.abstraction.deniable;

import cloud.swiftnode.kvaccine.abstraction.Deniable;
import cloud.swiftnode.kvaccine.abstraction.DeniableInfo;
import cloud.swiftnode.kvaccine.abstraction.ExecuteDeniable;
import cloud.swiftnode.kvaccine.abstraction.Info;
import cloud.swiftnode.kvaccine.abstraction.convertor.ObjectToDeniableConverter;
import cloud.swiftnode.kvaccine.abstraction.convertor.ObjectToInfoConverter;
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
    public String getDenyMsg() {
        return deniable.getDenyMsg();
    }

    @Override
    public void setDenyMsg(String msg) {
        deniable.setDenyMsg(msg);
    }
}
