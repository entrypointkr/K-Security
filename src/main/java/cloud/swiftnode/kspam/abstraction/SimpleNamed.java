package cloud.swiftnode.kspam.abstraction;

/**
 * Created by Junhyeong Lim on 2017-01-11.
 */
public class SimpleNamed implements Named {
    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
