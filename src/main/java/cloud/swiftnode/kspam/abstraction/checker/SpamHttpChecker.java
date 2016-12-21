package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.SpamChecker;
import cloud.swiftnode.kspam.abstraction.convertor.SwiftnodeResultConvertor;
import cloud.swiftnode.kspam.storage.SpamStorage;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.URLs;

import java.net.URL;

/**
 * Created by EntryPoint on 2016-12-20.
 */
public class SpamHttpChecker extends SpamChecker {
    public SpamHttpChecker(SpamStorage storage) {
        super(storage);
    }

    @Override
    public boolean check() {
        try {
            // Get data
            URL url = URLs.COMMUNITY_API.toUrl(storage.getIp());
            String text = Static.readAllText(url);
            // Set data
            storage.setResult(new SwiftnodeResultConvertor(text).convert());
        } catch (Exception ex) {
            // Ignore
        }
        return false;
    }
}
