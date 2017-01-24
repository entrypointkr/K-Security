package cloud.swiftnode.kspam.abstraction.asm;

import com.avaje.ebean.enhance.asm.AnnotationVisitor;
import com.avaje.ebean.enhance.asm.Attribute;
import com.avaje.ebean.enhance.asm.ClassVisitor;
import com.avaje.ebean.enhance.asm.FieldVisitor;
import com.avaje.ebean.enhance.asm.MethodVisitor;

/**
 * Created by Junhyeong Lim on 2017-01-24.
 */
public class KClassVisitor implements ClassVisitor {
    public boolean find = false;
    private KMethodVisitor methodVisitor = new KMethodVisitor();

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

    }

    @Override
    public void visitSource(String source, String debug) {

    }

    @Override
    public void visitOuterClass(String owner, String name, String desc) {

    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return null;
    }

    @Override
    public void visitAttribute(Attribute attr) {

    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {

    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        return null;
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
