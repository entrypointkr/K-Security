package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.util.OS;

/**
 * Created by EntryPoint on 2016-12-24.
 */
public class OSConvertor implements Convertor<OS> {
    private String os;

    public OSConvertor(String os) {
        this.os = os.toLowerCase();
    }

    @Override
    public OS convert() {
        if (os.contains("win")) {
            return OS.WINDOWS;
        } else if (os.contains("nux") || os.contains("nix")) {
            return OS.LINUX;
        } else {
            return OS.UNKNOWN;
        }
    }
}
