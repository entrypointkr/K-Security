package cloud.swiftnode.ksecurity.abstraction;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Junhyeong Lim on 2017-02-11.
 */
public class StorageCountDownLatch<T> extends CountDownLatch {
    private T value;

    public StorageCountDownLatch(int count) {
        super(count);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
