package kr.rvs.ksecurity.util;

import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Junhyeong Lim on 2017-10-19.
 */
public class Version {
    private static final Pattern PATTERN = Pattern.compile("(\\d+)(?:\\.(\\d+))?(?:\\.(\\d+))?(?:-(.*))?");
    public static final Version BUKKIT = new Version(Bukkit.getBukkitVersion());
    public static Version PLUGIN = new Version(4, 0, 0);
    public static String RELEASE = "RELEASE";
    private int major = 0;
    private int minor = 0;
    private int maintenance = 0;
    private String tag = RELEASE;

    public static void init(String version) {
        PLUGIN = new Version(version);
    }

    public Version(int major, int minor, int maintenance, String tag) {
        this.major = major;
        this.minor = minor;
        this.maintenance = maintenance;
        this.tag = tag;
    }

    public Version(int major, int minor, int maintenance) {
        this(major, minor, maintenance, RELEASE);
    }

    public Version(String version) {
        Matcher matcher = PATTERN.matcher(version);
        if (matcher.find()) {
            String majorStr = matcher.group(1);
            String minorStr = matcher.group(2);
            String maintenance = matcher.group(3);
            String tag = matcher.group(4);

            if (majorStr != null)
                this.major = Integer.parseInt(majorStr);
            if (minorStr != null)
                this.minor = Integer.parseInt(minorStr);
            if (maintenance != null)
                this.maintenance = Integer.parseInt(maintenance);
            if (tag != null)
                this.tag = tag.toUpperCase();
        }
    }

    public boolean isRelease() {
        return tag.equalsIgnoreCase(RELEASE);
    }

    public boolean after(Version version) {
        return major > version.major || minor > version.minor || maintenance > version.maintenance
                || isRelease() && !version.isRelease();
    }

    public boolean afterEquals(Version version) {
        return equals(version) || after(version);
    }

    public boolean before(Version version) {
        return major < version.major || minor < version.minor || maintenance < version.maintenance
                || !isRelease() && version.isRelease();
    }

    public boolean beforeEquals(Version version) {
        return equals(version) || before(version);
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

    public String getTag() {
        return tag;
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
