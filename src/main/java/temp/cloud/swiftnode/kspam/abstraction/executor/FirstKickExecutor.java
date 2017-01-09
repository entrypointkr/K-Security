package temp.cloud.swiftnode.kspam.abstraction.executor;

import temp.cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import temp.cloud.swiftnode.kspam.util.Lang;
import temp.cloud.swiftnode.kspam.util.Tracer;

/**
 * Created by Junhyeong Lim on 2017-01-09.
 */
public class FirstKickExecutor extends DelayedExecutor {
    @Override
    public void delayedExecute(Tracer tracer, DeniableInfoAdapter adapter, long startTime) {
        if (tracer.getResult() == Tracer.Result.DENY) {
            adapter.setKickMsg(Lang.FIRST_LOGIN_KICK.toString());
            adapter.deny();
        }
    }
}
