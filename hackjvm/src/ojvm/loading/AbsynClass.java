package ojvm.loading;
                         
import java.lang.reflect.Modifier;

import ojvm.util.RuntimeConstants;
import ojvm.util.Descriptor;

/**
 * The result of loading a class and doing a small amount of verification ...
 *
 * @author Amr Sabry
 * @version jdk-1.1 
 */

public class AbsynClass {
    private ConstantPool constant_pool;
    private int access_flags;
    private Descriptor classDesc, superClassDesc; // superClassName could be null
    private Descriptor[] interfaceDescs;
    private AbsynField[] declaredFields; 
    private AbsynMethod[] declaredMethods; 

    // Getters 
    public Descriptor getDesc () { return classDesc; }
    public Descriptor getSuperClassDesc () { return superClassDesc; }
    public Descriptor[] getInterfaceDescs () { return interfaceDescs; }
    public AbsynField[] getDeclaredFields () { return declaredFields; }
    public AbsynMethod[] getDeclaredMethods () { return declaredMethods; }

    public boolean isPublic () { return Modifier.isPublic(access_flags); }
    public boolean isFinal () { return Modifier.isFinal(access_flags); }
    public boolean accSuperSet () { return (access_flags & RuntimeConstants.ACC_SUPER) == 0; }
    public boolean isInterface () { return Modifier.isInterface(access_flags); }
    public boolean isAbstract () { return Modifier.isAbstract(access_flags); }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // Constructor: reads the binary representation of the class, and
    // does minimal verification (essentially pass 1 of the
    // verification; the remaining passes of verification are done by
    // the Linker). In more detail, the things that are verified are:
    // - check for correct magic number and supported versions
    // - file not truncated or has extra bytes at the end
    // - all attributes have the right length
    // - everything that points to the constant pool finds something of the expected type

    public AbsynClass (ClassInputStream classFile) throws LoadE {
        int magic = classFile.readU4();
        int minor_version = classFile.readU2(); 
        int major_version = classFile.readU2();

        if (magic != RuntimeConstants.JAVA_MAGIC) {
            throw new BadMagicE("Incorrect magic number " + magic);
        }
        if (major_version < RuntimeConstants.JAVA_VERSION_MIN2 || 
            major_version > RuntimeConstants.JAVA_VERSION_MAX2)
            throw new BadVersionE(major_version + "." + minor_version);

        this.constant_pool = new ConstantPool(classFile); // all indices are resolved to names
        this.access_flags = classFile.readU2();

        int class_index = classFile.readU2();
        classDesc = constant_pool.getClassEntry(class_index).getDesc();

        int superclass_index = classFile.readU2();
        if (superclass_index != 0) superClassDesc = constant_pool.getClassEntry(superclass_index).getDesc();

        // interfaces
        int interfaces_count = classFile.readU2();
        this.interfaceDescs = new Descriptor[interfaces_count];
        for (int i=0; i<interfaces_count; i++) {
            int interface_index = classFile.readU2();
            interfaceDescs[i] = constant_pool.getClassEntry(interface_index).getDesc();
        }

        // fields
        int field_count = classFile.readU2();
        this.declaredFields = new AbsynField[field_count];
        for (int i=0; i<field_count; i++) declaredFields[i] = new AbsynField(classFile, constant_pool);

        // methods
        int method_count = classFile.readU2();
        this.declaredMethods = new AbsynMethod[method_count];
        for (int i=0; i<method_count; i++)
                declaredMethods[i] = new AbsynMethod(classFile, constant_pool);

        // attributes 
        int attributes_count = classFile.readU2();
        for (int i=0; i<attributes_count; i++) // read and ignore all class attributes
            AbsynAttribute.read(classFile, constant_pool);

        // Nothing more allowed
        if (classFile.hasMoreBytes()) 
            throw new ClassFileInputStreamE("Class file " + classFile.getFilename() + " has extra bytes");
    }

    public String toString () { 
        String res = "";
        res += isPublic() ? "public " : "";
        res += isFinal() ? "final " : "";
        res += accSuperSet() ? "ACC_SUPER " : "";
        res += isInterface() ? "interface " : "";
        res += isAbstract() ? "abstract " : "";
        res += "class " + getDesc() + " ";
        res += "extends " + getSuperClassDesc() + " ";
        res += "implements ";
        for (int i=0; i<interfaceDescs.length; i++) 
            res += interfaceDescs[i] + " ";
        res += "\n";
        for (int i=0; i<declaredFields.length; i++) 
            res += declaredFields[i] + "\n";
        for (int i=0; i<declaredMethods.length; i++) 
            res += declaredMethods[i] + "\n";
        return res;
    }
}
