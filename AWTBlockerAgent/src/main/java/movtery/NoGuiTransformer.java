package movtery;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class NoGuiTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classFileBuffer) {

        if (className == null) return null;

        if (className.startsWith("javax/swing/") || className.startsWith("java/awt/")) {
            try {
                ClassPool pool = ClassPool.getDefault();
                CtClass ctClass = pool.makeClass(new ByteArrayInputStream(classFileBuffer));

                if (!ctClass.isInterface() && !ctClass.isAnnotation()) {
                    for (CtConstructor constructor : ctClass.getDeclaredConstructors()) {
                        constructor.setBody("{ throw new UnsupportedOperationException(\"Graphical UI is not allowed: \" + this.getClass().getName()); }");
                    }

                    return ctClass.toBytecode();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return classFileBuffer;
    }
}
