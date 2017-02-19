package cloud.swiftnode.ksecurity.util.factory;

import cloud.swiftnode.ksecurity.module.kvaccine.abstraction.asm.KPluginManagerClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.ASM5;

/**
 * Created by Junhyeong Lim on 2017-02-18.
 */
public class InterceptorFactory {
    public static Class<?> createKPluginManager() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String clsName = "org.bukkit.plugin.SimplePluginManager";
        Class<?> ret;

        ClassReader reader = new ClassReader(clsName);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES);
        ClassVisitor visitor = new KPluginManagerClassTransformer(ASM5, writer);

        reader.accept(visitor, 0);

        ClassLoader loader = InterceptorFactory.class.getClassLoader();

        try {
            Method defineMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
            defineMethod.setAccessible(true);
            ret = (Class) defineMethod.invoke(loader, clsName, writer.toByteArray(), 0, writer.toByteArray().length);
        } catch (Exception ex) {
            ret = loader.loadClass(clsName);
        }

        OutputStream out = new FileOutputStream(new File("new.class"));
        out.write(writer.toByteArray());

        return ret;
    }
}
