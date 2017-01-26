package cloud.swiftnode.kvaccine.abstraction;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public interface Named {
    default String name() {
        return this.getClass().getSimpleName();
    }
}
