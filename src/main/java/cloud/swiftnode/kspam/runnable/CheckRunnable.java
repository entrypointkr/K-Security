package cloud.swiftnode.kspam.runnable;

import cloud.swiftnode.kspam.storage.CheckStorage;
import cloud.swiftnode.kspam.util.Result;
import cloud.swiftnode.kspam.util.Static;
import cloud.swiftnode.kspam.util.URLs;

import java.net.URL;

/**
 * Created by EntryPoint on 2016-12-17.
 */
public class CheckRunnable implements Runnable {
    private CheckStorage storage;

    public CheckRunnable(CheckStorage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        try {
            // Get data
            URL url = URLs.COMMUNITY_API.toUrl(storage.getIp());
            String text = Static.readAllText(url);
            // Set data
            storage.setResult(Result.valueOf(text.toUpperCase()));
        } catch (Exception ex) {
            // Ignore
        }
    }
}
