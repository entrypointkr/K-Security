package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.abstraction.Processor;
import cloud.swiftnode.kspam.util.Config;
import cloud.swiftnode.kspam.util.Lang;
import cloud.swiftnode.kspam.util.Static;
import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 * Created by Junhyeong Lim on 2017-01-16.
 */
public class ShutdownProcessor implements Processor {
    @Override
    public boolean process() {
        if (Config.isShutdownWhenDisable() && Static.isRunning()) {
            //OP가 플러그인 종료후 봇테러 날리는일 방지
            Bukkit.broadcastMessage(Lang.DISABLED.builder().prefix().build());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Static.consoleMsg(ex);
                return false;
            }
            Bukkit.savePlayers();
            for (World world : Bukkit.getWorlds()) {
                world.save();
            }
            Bukkit.shutdown();
        }
        return true;
    }
}
