package cloud.swiftnode.kspam.abstraction;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public interface Info {
    public String getName();
    public String getIp();
    public String getUniqueId() throws IllegalStateException;
}
