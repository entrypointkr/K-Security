package cloud.swiftnode.kspam.abstraction.convertor;

import cloud.swiftnode.kspam.abstraction.ResultConvertor;
import cloud.swiftnode.kspam.util.Result;

/**
 * Created by horyu on 2016-12-23.
 */
public class MCBlacklistResultConvertor extends ResultConvertor {
    public MCBlacklistResultConvertor(String target) {
        super(target);
    }

    @Override
    public Result convert() {
        if (target == null) {
            return Result.ERROR;
        }

        String resultText = target.replace(" ", "");
        if (resultText.contains("\"error\":true")) {
            return Result.ERROR;
        } else if (resultText.contains("\"blacklist\":true")) {
            return Result.TRUE;
        } else if (resultText.contains("\"blacklist\":false")) {
            return Result.FALSE;
        }

        return Result.ERROR;
    }
}