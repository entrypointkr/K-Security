package cloud.swiftnode.kspam.util;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by EntryPoint on 2016-12-19.
 */
public class Version {
    private int major = 0;
    private int minor = 0;
    private int add = 0;
    private String tag;

    public Version(String version) {
        set(version);
    }

    public void set(String version) {
        Pattern pattern = Pattern.compile("(\\d+)(?:\\.(\\d+))?(?:\\.(\\d+))?(?:-(.*))?");
        Matcher matcher = pattern.matcher(version);
        if (matcher.find()) {
            String major = matcher.group(1);
            String minor = matcher.group(2);
            String add = matcher.group(3);
            if (major != null) {
                this.major = Integer.parseInt(major);
            }
            if (minor != null) {
                this.minor = Integer.parseInt(minor);
            }
            if (add != null) {
                this.add = Integer.parseInt(add);
            }
            tag = matcher.group(4);
        }
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public int getAdd() {
        return add;
    }

    public boolean beforeEquals(Version version) {
        if (major != version.getMajor()) {
            return major <= version.getMajor();
        }
        if (minor != version.getMinor()) {
            return minor <= version.getMinor();
        }
        return add <= version.getAdd();
    }

    public boolean after(Version version) {
        return !beforeEquals(version);
    }

    public boolean isTagged() {
        return tag != null;
    }

    @Override
    public String toString() {
        String ver = StringUtils.join(new Object[]{major, minor, add}, ".");
        if (isTagged()) {
            ver += "-" + tag;
        }
        return ver;
    }
}
