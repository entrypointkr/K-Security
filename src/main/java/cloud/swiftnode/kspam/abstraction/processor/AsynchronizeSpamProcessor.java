package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.checker.SwiftnodeChecker;
import cloud.swiftnode.kspam.abstraction.deniable.DeniableInfoAdapter;
import cloud.swiftnode.kspam.util.Tracer;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class AsynchronizeSpamProcessor extends SpamProcessor {
    public AsynchronizeSpamProcessor(DeniableInfoAdapter adapter, Tracer tracer) {
        super(adapter, tracer);
        super.addChecker(new SwiftnodeChecker(adapter));
    }
}
