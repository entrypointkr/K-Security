package cloud.swiftnode.kspam.abstraction;

import cloud.swiftnode.kspam.reflection.ClassProbe;
import cloud.swiftnode.kspam.reflection.Testable;
import cloud.swiftnode.kspam.storage.ReflectionStorage;
import cloud.swiftnode.kspam.storage.SpamStorage;
import cloud.swiftnode.kspam.util.Result;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by EntryPoint on 2016-12-24.
 */
public class SpamCheckerTest {
    @Test
    public void run() throws Exception {
        ClassProbe probe = new ClassProbe(
                new ReflectionStorage(ClassProbe.class.getClassLoader(),
                        "cloud.swiftnode.kspam.abstraction"));
        SpamStorage storage = new SpamStorage(Result.FALSE, "142.0.99.200");
        for (Class clazz : probe.getClassesWithAnnotation(Testable.class)) {
            System.out.println(clazz.toString());
            if (SpamChecker.class.isAssignableFrom(clazz) && !SpamChecker.class.equals(clazz)) {
                SpamChecker checker = (SpamChecker) clazz.getConstructor(SpamStorage.class).newInstance(storage);
                checker.check();
                System.out.println(storage.getResult());
                assertNotEquals(storage.getResult(), Result.ERROR);
            }
        }
    }
}