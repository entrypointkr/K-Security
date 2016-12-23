package cloud.swiftnode.kspam.abstraction.convertor;

import cloud.swiftnode.kspam.abstraction.ResultConvertor;
import cloud.swiftnode.kspam.util.Result;

/**
 * Created by EntryPoint on 2016-12-22.
 */
public class BotscoutResultConvertor extends ResultConvertor {
    public BotscoutResultConvertor(String target) {
        super(target);
    }

    @Override
    public Result convert() {
        if (target.contains("Y|")) {
            return Result.TRUE;
        } else if (target.contains("N|")) {
            return Result.FALSE;
        }
        return Result.ERROR;
    }
}
