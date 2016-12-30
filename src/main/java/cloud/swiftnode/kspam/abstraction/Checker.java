package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.util.Tracer;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public interface Checker {
    Tracer.Result check();
}
