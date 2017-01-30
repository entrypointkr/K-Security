package cloud.swiftnode.ksecurity.abstraction;

/**
 * Created by Junhyeong Lim on 2017-01-10.
 */
public abstract class StringConverter<T> implements Converter<T> {
    protected String str;

    public StringConverter(String str) {
        this.str = str;
    }
}
