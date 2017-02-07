package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.asm;

import org.objectweb.asm.MethodVisitor;

/**
 * Created by Junhyeong Lim on 2017-01-24.
 */
public class KMethodVisitor extends MethodVisitor {
    public boolean find = false;

    public KMethodVisitor(int api) {
        super(api);
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        if (type.contains("java/net") && type.endsWith("Socket")) {
            find = true;
        }
    }
}
