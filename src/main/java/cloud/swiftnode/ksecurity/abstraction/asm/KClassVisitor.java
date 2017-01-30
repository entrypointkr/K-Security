package cloud.swiftnode.ksecurity.abstraction.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * Created by Junhyeong Lim on 2017-01-24.
 */
public class KClassVisitor extends ClassVisitor {
    public boolean find = false;
    private KMethodVisitor methodVisitor;

    public KClassVisitor(int api) {
        super(api);
        this.methodVisitor = new KMethodVisitor(api);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return methodVisitor;
    }

    @Override
    public void visitEnd() {
        find = methodVisitor.find;
    }
}
