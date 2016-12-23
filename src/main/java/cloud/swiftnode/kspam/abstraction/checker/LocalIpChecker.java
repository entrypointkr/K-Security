package cloud.swiftnode.kspam.abstraction.checker;

import cloud.swiftnode.kspam.abstraction.StringChecker;

/**
 * Created by EntryPoint on 2016-12-23.
 */
public class LocalIpChecker extends StringChecker {
    public LocalIpChecker(String str) {
        super(str);
    }

    @Override
    public boolean check() {
        return str.contains("127.0") || str.contains("192.168");
    }
}
