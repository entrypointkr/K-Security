package cloud.swiftnode.kspam.abstraction;

/**
 * Created by EntryPoint on 2016-12-30.
 */
public abstract class Storage<T> {
    protected T value;

    public Storage(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
