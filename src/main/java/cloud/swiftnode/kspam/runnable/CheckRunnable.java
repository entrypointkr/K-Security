package cloud.swiftnode.kspam.runnable;

import cloud.swiftnode.kspam.storage.CheckStorage;
import cloud.swiftnode.kspam.util.Result;
import cloud.swiftnode.kspam.util.Static;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
            URL url = Static.getUrl(storage.getIp());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String line;
            String all = "";
            while ((line = reader.readLine()) != null) {
                all += line;
            }
            // Set result
            storage.setResult(Result.valueOf(all.toUpperCase()));
        } catch (Exception ex) {
            // Ignore
        }
    }
}
