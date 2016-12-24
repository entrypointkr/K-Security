package cloud.swiftnode.kspam.storage;

/**
 * Created by EntryPoint on 2016-12-24.
 */
public class ReflectionStorage {
    private ClassLoader loader;
    private String packageName;

    public ReflectionStorage(ClassLoader loader, String packageName) {
        this.loader = loader;
        this.packageName = packageName;
    }

    public ClassLoader getLoader() {
        return loader;
    }

    public String getPackageName() {
        return packageName;
    }
}
