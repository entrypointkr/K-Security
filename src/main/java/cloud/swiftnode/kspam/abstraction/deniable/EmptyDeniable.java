package cloud.swiftnode.kspam.abstraction.deniable;

import cloud.swiftnode.kspam.abstraction.Deniable;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class EmptyDeniable implements Deniable {
    @Override
    public void deny() {
        // Empty
    }
}
