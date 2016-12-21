package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.util.Result;

/**
 * Created by EntryPoint on 2016-12-21.
 */
public abstract class ResultConvertor implements Convertor<Result> {
    protected String target;

    public ResultConvertor(String target) {
        this.target = target;
    }
}
