package cloud.swiftnode.kspam.abstraction.processor;

import cloud.swiftnode.kspam.abstraction.Deniable;
import cloud.swiftnode.kspam.abstraction.SpamProcessor;
import cloud.swiftnode.kspam.abstraction.checker.CacheChecker;
import cloud.swiftnode.kspam.abstraction.checker.TempChecker;
import cloud.swiftnode.kspam.util.Tracer;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public class SynchronizeSpamProcessor extends SpamProcessor {
    public SynchronizeSpamProcessor(Deniable deniable, Tracer tracer) {
        super(deniable, tracer);
        super.addChecker(
                new CacheChecker(),
                new TempChecker()
        );
        // 위에 체커 추가
    }
}
