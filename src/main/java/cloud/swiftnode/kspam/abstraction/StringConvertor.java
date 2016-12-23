package cloud.swiftnode.kspam.abstraction;

/**
 * Created by EntryPoint on 2016-12-22.
 */
public abstract class StringConvertor implements Convertor<String> {
    protected Object obj;

    public StringConvertor(Object obj) {
        this.obj = obj;
    }
}
