package cloud.swiftnode.kspam.abstraction.convertor;

import cloud.swiftnode.kspam.abstraction.StringConvertor;
import org.bukkit.entity.Player;

import java.net.InetAddress;

/**
 * Created by EntryPoint on 2016-12-22.
 */
public class IpStringConvertor extends StringConvertor {
    public IpStringConvertor(Object obj) {
        super(obj);
    }

    @Override
    public String convert() {
        String ret = "";
        if (obj instanceof InetAddress) {
            ret = obj.toString();
            ret = ret.substring(ret.indexOf("/") + 1);
        } else if (obj instanceof Player) {
            ret = ((Player) obj).getAddress().getAddress().toString();
            ret = ret.substring(ret.indexOf("/") + 1);
        }
        return ret;
    }
}
