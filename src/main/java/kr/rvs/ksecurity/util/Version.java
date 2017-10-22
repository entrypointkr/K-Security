package kr.rvs.ksecurity.util;

import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Junhyeong Lim on 2017-10-19.
 */
public class Version {
    private static final Pattern PATTERN = Pattern.compile("(\\d+)(?:\\.(\\d+))?(?:\\.(\\d+))?");
    public static final Version BUKKIT = new Version(Bukkit.getBukkitVersion());
    public static Version PLUGIN = new Version(4, 0, 0);
    private int major = 0;
    private int minor = 0;
    private int maintenance = 0;

    public static void init(String version) {
        PLUGIN = new Version(version);
    }

    public Version(int major, int minor, int maintenance) {
        this.major = major;
        this.minor = minor;
        this.maintenance = maintenance;
    }

    public Version(String version) {
        Matcher matcher = PATTERN.matcher(version);
        if (matcher.find()) {
            String majorStr = matcher.group(1);
            String minorStr = matcher.group(2);
            String maintenance = matcher.group(3);

            if (majorStr != null)
                this.major = Integer.parseInt(majorStr);
            if (minorStr != null)
                this.minor = Integer.parseInt(minorStr);
            if (maintenance != null)
                this.maintenance = Integer.parseInt(maintenance);
        }
    }

    public boolean after(Version version) {
        return major > version.major || minor > version.minor || maintenance > version.maintenance;
    }

    public boolean afterEquals(Version version) {
        return major == version.major ? minor == version.minor ?
                maintenance >= version.maintenance : minor >= version.minor : major >= version.major;
    }

    public boolean before(Version version) {
        return major < version.major || minor < version.minor || maintenance < version.maintenance;
    }

    public boolean beforeEquals(Version version) {
        return major == version.major ? minor == version.minor ?
                maintenance <= version.maintenance : minor <= version.minor : major <= version.minor;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getMaintenance() {
        return maintenance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Version version = (Version) o;

        if (major != version.major) return false;
        if (minor != version.minor) return false;
        return maintenance == version.maintenance;
    }

    @Override
    public int hashCode() {
        int result = major;
        result = 31 * result + minor;
        result = 31 * result + maintenance;
        return result;
    }

    @Override
    public String toString() {
        String target = maintenance > 0 ? "%d.%d.%d" : "%d.%d";
        Object[] args = maintenance > 0 ? new Integer[] {major, minor, maintenance} : new Integer[] {major, minor};
        return String.format(target, args);
    }
}
