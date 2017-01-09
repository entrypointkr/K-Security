package temp.cloud.swiftnode.kspam.abstraction;

import temp.cloud.swiftnode.kspam.util.Tracer;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public interface Checker {
    Tracer.Result check() throws Exception;

    String name();
}
