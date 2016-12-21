package cloud.swiftnode.kspam.abstraction.convertor;

import cloud.swiftnode.kspam.abstraction.ResultConvertor;
import cloud.swiftnode.kspam.util.Result;
import cloud.swiftnode.kspam.util.Static;

/**
 * Created by EntryPoint on 2016-12-21.
 */
public class StopforumResultConvertor extends ResultConvertor {
    public StopforumResultConvertor(String target) {
        super(target);
    }

    @Override
    public Result convert() {
        try {
            String success = Static.substring(target, "<response success=\"", "\">");
            if (success.equals("false")) {
                return Result.ERROR;
            }
            String appears = Static.substring(target, "<appears>", "</appears>");
            if (appears.equals("yes")) {
                return Result.TRUE;
            } else if (appears.equals("no")) {
                return Result.FALSE;
            }
        } catch (Exception ex) {
            // Ignore
        }
        return Result.ERROR;
    }
}
