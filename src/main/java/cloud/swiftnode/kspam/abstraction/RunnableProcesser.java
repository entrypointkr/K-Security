package cloud.swiftnode.kspam.abstraction;

/**
 * Created by EntryPoint on 2016-12-21.
 */
public abstract class RunnableProcesser implements Processer {
    @Override
    public void run() {
        this.process();
    }
}
