package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * Created by Junhyeong Lim on 2017-02-18.
 */
public class KPluginManagerClassTransformer extends ClassVisitor {
    public KPluginManagerClassTransformer(int i, ClassVisitor classVisitor) {
        super(i, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor visitor = super.visitMethod(access, name, desc, signature, exceptions);
        if (name.equals("disablePlugin")) {
            visitor = new KPluginManagerMethodTransformer(api, visitor);
        }
        return visitor;
    }
}
