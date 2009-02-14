package ojvm.loading;
                         
import java.lang.reflect.Modifier; 

import ojvm.util.Descriptor;
import ojvm.util.MethodDescriptor;
import ojvm.util.BadDescriptorE;

/**
 * The result of loading and doing a small amount of verification on a method...
 *
 * @author Amr Sabry
 * @version jdk-1.1 
 */

public class AbsynMethod {
    private int access_flags; 
    private String name;
    private MethodDescriptor desc;
    private CodeAttribute code; // may be null for native or abstract method
    private ExceptionsAttribute exceptions; // may be null if throws clause is empty

    public boolean isPublic () { return Modifier.isPublic(access_flags); }
    public boolean isPrivate () { return Modifier.isPrivate(access_flags); }
    public boolean isProtected () { return Modifier.isProtected(access_flags); }
    public boolean isStatic () { return Modifier.isStatic(access_flags); }
    public boolean isFinal () { return Modifier.isFinal(access_flags); }
    public boolean isSynchronized () { return Modifier.isSynchronized(access_flags); }
    public boolean isNative () { return Modifier.isNative(access_flags); }
    public boolean isAbstract () { return Modifier.isAbstract(access_flags); }
    public boolean isStrict () { return Modifier.isStrict(access_flags); }
    public boolean isConstructor () { return name.equals("<init>"); }
    public boolean isClassInitializer () { return name.equals("<clinit>"); }

    public String getName () { return name; }
    public MethodDescriptor getType () { return desc; }
    public CodeAttribute getCode () { return code; }
    public ExceptionsAttribute getExceptions () { return exceptions; }

    AbsynMethod (ClassInputStream classFile, ConstantPool cp) throws ClassFileInputStreamE, ConstantPoolE {
        try {
            access_flags = classFile.readU2();
        
            int name_index = classFile.readU2();
            name = cp.getUtf8Entry(name_index).getString();

            int descriptor_index = classFile.readU2();
            desc = new MethodDescriptor(cp.getUtf8Entry(descriptor_index).getString());

            int attributes_count = classFile.readU2();
            AbsynAttribute[] attributes = new AbsynAttribute[attributes_count];
            for (int i=0; i<attributes_count; i++)
                attributes[i] = AbsynAttribute.read(classFile, cp);
            for (int i=0; i<attributes_count; i++) {
                if (attributes[i].getName().equals("Code")) {
                    if (code != null) 
                        throw new ConstantPoolE("More than one \"Code\" attribute in constant pool");
                    code = (CodeAttribute)attributes[i];
                }
                if (attributes[i].getName().equals("Exceptions")) {
                    if (exceptions != null) 
                        throw new ConstantPoolE("More than one \"Exceptions\" attribute in constant pool");
                    exceptions = (ExceptionsAttribute)attributes[i];
                }
            }
        }
        catch (BadDescriptorE e) { 
            throw new ConstantPoolE("Bad method descriptor in constant pool " + e.getMessage());
        }
    }

    public String toString () { 
        String res = "";
        res += isPublic() ? "public " : "";
        res += isPrivate() ? "private " : "";
        res += isProtected() ? "protected " : "";
        res += isStatic() ? "static " : "";
        res += isFinal() ? "final " : "";
        res += isSynchronized() ? "synchronized " : "";
        res += isNative() ? "native " : "";
        res += isAbstract() ? "abstract " : "";
        res += isStrict() ? "strict " : "";
        res += " " + name + " : " + desc + " throws " + exceptions + "\n";
        res += "Code: \n" + code + "\n";
        return res;
    }
}

