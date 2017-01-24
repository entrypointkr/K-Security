package cloud.swiftnode.kspam.abstraction.asm;

import com.avaje.ebean.enhance.asm.AnnotationVisitor;
import com.avaje.ebean.enhance.asm.Attribute;
import com.avaje.ebean.enhance.asm.Label;
import com.avaje.ebean.enhance.asm.MethodVisitor;

/**
 * Created by Junhyeong Lim on 2017-01-24.
 */
public class KMethodVisitor implements MethodVisitor {
    public boolean find = false;

    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        return null;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return null;
    }

    @Override
    public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
        return null;
    }

    @Override
    public void visitAttribute(Attribute attr) {

    }

    @Override
    public void visitCode() {

    }

    @Override
    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {

    }

    @Override
    public void visitInsn(int opcode) {

    }

    @Override
    public void visitIntInsn(int opcode, int operand) {

    }

    @Override
    public void visitVarInsn(int opcode, int var) {

    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        if (type.equalsIgnoreCase("java/net/Socket")) {
            find = true;
        }
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {

    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {

    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {

    }

    @Override
    public void visitLabel(Label label) {

    }

    @Override
    public void visitLdcInsn(Object cst) {

    }

    @Override
    public void visitIincInsn(int var, int increment) {

    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {

    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {

    }

    @Override
    public void visitMultiANewArrayInsn(String desc, int dims) {

    }

    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {

    }

    @Override
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {

    }

    @Override
    public void visitLineNumber(int line, Label start) {
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {

    }

    @Override
    public void visitEnd() {

    }
}
