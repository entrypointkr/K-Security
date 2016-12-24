package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.convertor.SwiftnodeResultConvertor;
import cloud.swiftnode.kspam.reflection.TestEscape;
import cloud.swiftnode.kspam.storage.SpamStorage;
import cloud.swiftnode.kspam.util.Result;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.Type;
import cloud.swiftnode.kspam.util.URLs;

import java.net.URL;

/**
 * Created by EntryPoint on 2016-12-20.
 */
public class SpamSwiftnodeChecker extends SpamChecker {
    public SpamSwiftnodeChecker(SpamStorage storage) {
        super(storage);
    }

    @Override
    public boolean check() {
        if (storage.getResult() == Result.TRUE) {
            return true;
        }
        try {
            URL url = URLs.COMMUNITY_API.toUrl(storage.getIp());
            String text = Static.readAllText(url);
            storage.setResult(new SwiftnodeResultConvertor(text).convert());
        } catch (Exception ex) {
            storage.setResult(Result.ERROR);
        }
        storage.setType(Type.SWIFTNODE);
        return false;
    }
}
