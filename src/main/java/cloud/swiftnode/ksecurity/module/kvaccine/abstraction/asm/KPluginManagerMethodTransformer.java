package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.AASTORE;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ANEWARRAY;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.INVOKEINTERFACE;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;
import static org.objectweb.asm.Opcodes.INVOKEVIRTUAL;
import static org.objectweb.asm.Opcodes.POP;
import static org.objectweb.asm.Opcodes.RETURN;

/**
 * Created by Junhyeong Lim on 2017-02-18.
 */
public class KPluginManagerMethodTransformer extends MethodVisitor {
    public KPluginManagerMethodTransformer(int i, MethodVisitor methodVisitor) {
        super(i, methodVisitor);
    }

    @Override
    public void visitCode() {
        Label l0 = new Label();
        visitLabel(l0);
        visitLineNumber(254, l0);
        visitVarInsn(ALOAD, 1);
        visitMethodInsn(INVOKEINTERFACE, "org/bukkit/plugin/Plugin", "getName", "()Ljava/lang/String;", true);
        visitFieldInsn(GETSTATIC, "cloud/swiftnode/ksecurity/KSecurity", "inst", "Lorg/bukkit/plugin/Plugin;");
        visitMethodInsn(INVOKEINTERFACE, "org/bukkit/plugin/Plugin", "getName", "()Ljava/lang/String;", true);
        visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
        Label l1 = new Label();
        visitJumpInsn(IFEQ, l1);
        Label l2 = new Label();
        visitLabel(l2);
        visitLineNumber(255, l2);
        visitFieldInsn(GETSTATIC, "cloud/swiftnode/ksecurity/util/Lang", "SELF_DEFENCE", "Lcloud/swiftnode/ksecurity/util/Lang;");
        visitMethodInsn(INVOKEVIRTUAL, "cloud/swiftnode/ksecurity/util/Lang", "builder", "()Lcloud/swiftnode/ksecurity/util/Lang$MessageBuilder;", false);
        visitFieldInsn(GETSTATIC, "cloud/swiftnode/ksecurity/util/Lang$Key", "PLUGIN_NAME", "Lcloud/swiftnode/ksecurity/util/Lang$Key;");
        Label l3 = new Label();
        visitLabel(l3);
        visitLineNumber(256, l3);
        visitMethodInsn(INVOKESTATIC, "cloud/swiftnode/ksecurity/util/Static", "getRequestPlugin", "()Lorg/bukkit/plugin/Plugin;", false);
        visitMethodInsn(INVOKEINTERFACE, "org/bukkit/plugin/Plugin", "getName", "()Ljava/lang/String;", true);
        visitMethodInsn(INVOKEVIRTUAL, "cloud/swiftnode/ksecurity/util/Lang$MessageBuilder", "single", "(Lcloud/swiftnode/ksecurity/util/Lang$Key;Ljava/lang/Object;)Lcloud/swiftnode/ksecurity/util/Lang$MessageBuilder;", false);
        visitVarInsn(ASTORE, 2);
        Label l4 = new Label();
        visitLabel(l4);
        visitLineNumber(257, l4);
        visitInsn(ICONST_1);
        visitTypeInsn(ANEWARRAY, "cloud/swiftnode/ksecurity/util/Lang$MessageBuilder");
        visitInsn(DUP);
        visitInsn(ICONST_0);
        visitVarInsn(ALOAD, 2);
        visitInsn(AASTORE);
        visitMethodInsn(INVOKESTATIC, "cloud/swiftnode/ksecurity/util/Static", "log", "([Lcloud/swiftnode/ksecurity/util/Lang$MessageBuilder;)V", false);
        Label l5 = new Label();
        visitLabel(l5);
        visitLineNumber(258, l5);
        visitVarInsn(ALOAD, 2);
        visitMethodInsn(INVOKEVIRTUAL, "cloud/swiftnode/ksecurity/util/Lang$MessageBuilder", "build", "()Ljava/lang/String;", false);
        visitMethodInsn(INVOKESTATIC, "org/bukkit/Bukkit", "broadcastMessage", "(Ljava/lang/String;)I", false);
        visitInsn(POP);
        Label l6 = new Label();
        visitLabel(l6);
        visitLineNumber(259, l6);
        visitInsn(RETURN);
        visitLabel(l1);
    }
}
