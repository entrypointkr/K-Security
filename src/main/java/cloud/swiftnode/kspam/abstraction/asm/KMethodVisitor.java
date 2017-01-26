package cloud.swiftnode.kspam.abstraction.asm;

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
        if (type.equalsIgnoreCase("java/net/Socket")) {
            find = true;
        }
    }
}
