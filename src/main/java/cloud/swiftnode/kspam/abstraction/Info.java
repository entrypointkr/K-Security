package cloud.swiftnode.kspam.abstraction;

import java.util.UUID;

/**
 * Created by EntryPoint on 2017-01-04.
 */
public interface Info {
    String getIp();

    UUID getUniqueId();

    String getName();
}
