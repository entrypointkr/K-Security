package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.convertor.ShroomeryResultConvertor;
import cloud.swiftnode.kspam.abstraction.convertor.StopforumResultConvertor;
import cloud.swiftnode.kspam.storage.SpamStorage;
import cloud.swiftnode.kspam.util.Result;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.URLs;

import java.net.URL;

/**
 * Created by EntryPoint on 2016-12-21.
 */
public class ProxyHttpChecker extends SpamChecker {
    public ProxyHttpChecker(SpamStorage storage) {
        super(storage);
    }

    @Override
    public boolean check() {
        if (storage.getResult() == Result.TRUE) {
            return true;
        }
        try {
            // Shroomery
            URL url = URLs.SHROOMERY_API.toUrl(storage.getIp());
            String text = Static.readAllText(url);
            Result result = new ShroomeryResultConvertor(text).convert();
            if (result != Result.TRUE) {
                // StopForum
                url = URLs.STOPFORUM_API.toUrl(storage.getIp());
                text = Static.readAllText(url);
                result = new StopforumResultConvertor(text).convert();
            }
            // Set
            storage.setResult(result);
        } catch (Exception ex) {
            // Ignore
        }
        return true;
    }
}
