package ojvm.util;

/**
 * 
 * @author Amr Sabry 
 * @version jdk-1.1 
*/

public class CommonDescriptors {

    public static Descriptor javaLangObjectDesc;
    public static Descriptor javaLangClassDesc;
    public static Descriptor javaLangStringDesc;
    public static Descriptor javaLangSystemDesc;
    public static Descriptor javaLangClassLoaderDesc;
    public static Descriptor javaLangThreadDesc;
    public static Descriptor javaLangThreadGroupDesc;
    public static Descriptor javaLangCloneableDesc;
    public static Descriptor javaLangThrowableDesc;

    public static Descriptor javaIoFileInputStreamDesc;
    public static Descriptor javaIoFileOutputStreamDesc;
    public static Descriptor javaIoPrintStreamDesc;
    public static Descriptor javaIoFileDescriptorDesc;
    public static Descriptor javaLangMathDesc;
    public static Descriptor javaLangDoubleDesc;
    public static Descriptor javaLangFloatDesc;
    public static Descriptor javaIoSerializableDesc;

    public static Descriptor javaSecurityAccessControllerDesc;

    public static Descriptor booleanDesc;
    public static Descriptor byteDesc;
    public static Descriptor charDesc;
    public static Descriptor doubleDesc;
    public static Descriptor floatDesc;
    public static Descriptor intDesc;
    public static Descriptor longDesc;
    public static Descriptor shortDesc;
    public static Descriptor voidDesc;

    public static Descriptor stringArrayDesc;
    public static Descriptor charArrayDesc;

    static {
        try {
            int kind = Descriptor.JAVA_FORM;

            javaLangObjectDesc = new Descriptor("java.lang.Object", kind);
            javaLangClassDesc = new Descriptor("java.lang.Class", kind);
            javaLangStringDesc = new Descriptor("java.lang.String", kind);
            javaLangSystemDesc = new Descriptor("java.lang.System", kind);
            javaLangClassLoaderDesc = new Descriptor("java.lang.ClassLoader", kind);
            javaLangThreadDesc = new Descriptor("java.lang.Thread", kind);
            javaLangThreadGroupDesc = new Descriptor("java.lang.ThreadGroup", kind);
            javaLangCloneableDesc = new Descriptor("java.lang.Cloneable", kind);
            javaLangThrowableDesc = new Descriptor("java.lang.Throwable", kind);
            javaLangMathDesc = new Descriptor("java.lang.Math", kind);
            javaLangDoubleDesc = new Descriptor("java.lang.Double", kind);
            javaLangFloatDesc = new Descriptor("java.lang.Float", kind);

            javaIoFileInputStreamDesc = new Descriptor("java.io.FileInputStream", kind);
            javaIoFileOutputStreamDesc = new Descriptor("java.io.FileOutputStream", kind);
            javaIoPrintStreamDesc = new Descriptor("java.io.PrintStream", kind);
            javaIoFileDescriptorDesc = new Descriptor("java.io.FileDescriptor", kind);
            javaIoSerializableDesc = new Descriptor("java.io.Serializable", kind);

            javaSecurityAccessControllerDesc = new Descriptor("java.security.AccessController", kind);

            booleanDesc = new Descriptor("Z");
            byteDesc = new Descriptor("B");
            charDesc = new Descriptor("C");
            doubleDesc = new Descriptor("D");
            floatDesc = new Descriptor("F");
            intDesc = new Descriptor("I");
            longDesc = new Descriptor("J");
            shortDesc = new Descriptor("S");
            voidDesc = new Descriptor("V",true);

            stringArrayDesc = new Descriptor("[Ljava/lang/String;");
            charArrayDesc = new Descriptor("[C");
        }
        catch (BadDescriptorE e) {
            throw new Error("Bug when defining common descriptors");
        }
    }
}
