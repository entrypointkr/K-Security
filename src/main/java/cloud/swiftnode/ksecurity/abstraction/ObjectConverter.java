package cloud.swiftnode.ksecurity.abstraction;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class ObjectConverter<T> implements Converter<T> {
    protected Object obj;

    public ObjectConverter(Object obj) {
        this.obj = obj;
    }
}
