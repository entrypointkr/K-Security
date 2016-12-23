package cloud.swiftnode.kspam.abstraction.convertor;

import cloud.swiftnode.kspam.abstraction.ResultConvertor;
import cloud.swiftnode.kspam.util.Result;

/**
 * Created by EntryPoint on 2016-12-21.
 */
public class SwiftnodeResultConvertor extends ResultConvertor {
    public SwiftnodeResultConvertor(String target) {
        super(target);
    }

    @Override
    public Result convert() {
        try {
            return Result.valueOf(target.toUpperCase());
        } catch (Exception ex) {
            return Result.ERROR;
        }
    }
}
