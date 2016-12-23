package cloud.swiftnode.kspam.runnable;

import cloud.swiftnode.kspam.abstraction.processer.CacheReadProcesser;
import cloud.swiftnode.kspam.util.Path;
import cloud.swiftnode.kspam.util.URLs;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by EntryPoint on 2016-12-23.
 */
public class DownloadBukkitRunnable extends ErrorableBukkitRunnable {
    @Override
    public synchronized void run() {
        try {
            URL website = URLs.CACHE.toUrl();
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(Path.CACHE.toFile());
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            new CacheReadProcesser().process();
        } catch (Exception ex) {
            task(ex, "캐시 파일 다운로드 실패");
        }
    }
}
