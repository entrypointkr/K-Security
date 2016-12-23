package cloud.swiftnode.kspam.abstraction.convertor;

import cloud.swiftnode.kspam.abstraction.ResultConvertor;
import cloud.swiftnode.kspam.util.Result;

/**
 * Created by EntryPoint on 2016-12-21.
 */
public class ShroomeryResultConvertor extends ResultConvertor {
    public ShroomeryResultConvertor(String target) {
        super(target);
    }

    @Override
    public Result convert() {
        if (target.equals("Y")) {
            return Result.TRUE;
        } else if (target.equals("N")) {
            return Result.FALSE;
        }
        return Result.ERROR;
    }
}
