package cloud.swiftnode.ksecurity.module.kvaccine.abstraction.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.AASTORE;
import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ANEWARRAY;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.CHECKCAST;
import static org.objectweb.asm.Opcodes.DUP;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.GETSTATIC;
import static org.objectweb.asm.Opcodes.ICONST_0;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.IFEQ;
import static org.objectweb.asm.Opcodes.INSTANCEOF;
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
        visitVarInsn(ALOAD, 0);
        visitFieldInsn(GETFIELD, "org/bukkit/plugin/SimplePluginManager", "server", "Lorg/bukkit/Server;");
        visitTypeInsn(INSTANCEOF, "org/bukkit/craftbukkit/v1_8_R3/CraftServer");
        Label label = new Label();
        visitJumpInsn(IFEQ, label);
        visitVarInsn(ALOAD, 0);
        visitFieldInsn(GETFIELD, "org/bukkit/plugin/SimplePluginManager", "server", "Lorg/bukkit/Server;");
        visitTypeInsn(CHECKCAST, "org/bukkit/craftbukkit/v1_8_R3/CraftServer");
        visitVarInsn(ASTORE, 2);
        visitVarInsn(ALOAD, 2);
        visitMethodInsn(INVOKEVIRTUAL, "org/bukkit/craftbukkit/v1_8_R3/CraftServer", "getServer", "()Lnet/minecraft/server/v1_8_R3/MinecraftServer;", false);
        visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_8_R3/MinecraftServer", "isRunning", "()Z", false);
        visitJumpInsn(IFEQ, label);
        visitVarInsn(ALOAD, 1);
        visitMethodInsn(INVOKEINTERFACE, "org/bukkit/plugin/Plugin", "getName", "()Ljava/lang/String;", true);
        visitFieldInsn(GETSTATIC, "cloud/swiftnode/ksecurity/KSecurity", "inst", "Lorg/bukkit/plugin/Plugin;");
        visitMethodInsn(INVOKEINTERFACE, "org/bukkit/plugin/Plugin", "getName", "()Ljava/lang/String;", true);
        visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
        visitJumpInsn(IFEQ, label);
        visitFieldInsn(GETSTATIC, "cloud/swiftnode/ksecurity/util/Lang", "SELF_DEFENCE", "Lcloud/swiftnode/ksecurity/util/Lang;");
        visitMethodInsn(INVOKEVIRTUAL, "cloud/swiftnode/ksecurity/util/Lang", "builder", "()Lcloud/swiftnode/ksecurity/util/Lang$MessageBuilder;", false);
        visitFieldInsn(GETSTATIC, "cloud/swiftnode/ksecurity/util/Lang$Key", "PLUGIN_NAME", "Lcloud/swiftnode/ksecurity/util/Lang$Key;");
        visitMethodInsn(INVOKESTATIC, "cloud/swiftnode/ksecurity/util/Static", "getRequestPlugin", "()Lorg/bukkit/plugin/Plugin;", false);
        visitMethodInsn(INVOKEINTERFACE, "org/bukkit/plugin/Plugin", "getName", "()Ljava/lang/String;", true);
        visitMethodInsn(INVOKEVIRTUAL, "cloud/swiftnode/ksecurity/util/Lang$MessageBuilder", "single", "(Lcloud/swiftnode/ksecurity/util/Lang$Key;Ljava/lang/Object;)Lcloud/swiftnode/ksecurity/util/Lang$MessageBuilder;", false);
        visitVarInsn(ASTORE, 3);
        visitInsn(ICONST_1);
        visitTypeInsn(ANEWARRAY, "cloud/swiftnode/ksecurity/util/Lang$MessageBuilder");
        visitInsn(DUP);
        visitInsn(ICONST_0);
        visitVarInsn(ALOAD, 3);
        visitInsn(AASTORE);
        visitMethodInsn(INVOKESTATIC, "cloud/swiftnode/ksecurity/util/Static", "log", "([Lcloud/swiftnode/ksecurity/util/Lang$MessageBuilder;)V", false);
        visitVarInsn(ALOAD, 3);
        visitMethodInsn(INVOKEVIRTUAL, "cloud/swiftnode/ksecurity/util/Lang$MessageBuilder", "build", "()Ljava/lang/String;", false);
        visitMethodInsn(INVOKESTATIC, "org/bukkit/Bukkit", "broadcastMessage", "(Ljava/lang/String;)I", false);
        visitInsn(POP);
        visitInsn(RETURN);
        visitLabel(label);
    }
}
