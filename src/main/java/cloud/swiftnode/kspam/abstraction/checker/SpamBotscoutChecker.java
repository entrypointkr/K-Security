package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.convertor.BotscoutResultConvertor;
import cloud.swiftnode.kspam.storage.SpamStorage;
import cloud.swiftnode.kspam.util.Result;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.Type;
import cloud.swiftnode.kspam.util.URLs;

import java.net.URL;

/**
 * Created by EntryPoint on 2016-12-22.
 */
public class SpamBotscoutChecker extends SpamChecker {
    public SpamBotscoutChecker(SpamStorage storage) {
        super(storage);
    }

    @Override
    public boolean check() {
        if (storage.getResult() == Result.TRUE) {
            return true;
        }
        try {
            URL url = URLs.BOTSCOUT_API.toUrl(storage.getIp());
            String text = Static.readAllText(url);
            storage.setResult(new BotscoutResultConvertor(text).convert());
            storage.setType(Type.BOTSCOUNT);
        } catch (Exception ex) {
            // Ignore
        }
        return true;
    }
}
