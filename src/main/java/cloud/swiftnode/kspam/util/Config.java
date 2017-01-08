package cloud.swiftnode.kspam.util;

/**
 * Created by Junhyeong Lim on 2017-01-09.
 */
public enum Config {
    DEBUG_MODE("debug-mode");

    private final String key;

    Config(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}
