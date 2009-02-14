package ojvm.loading;
                         
import java.lang.reflect.Modifier; 

import ojvm.util.Descriptor;
import ojvm.util.BadDescriptorE;

/**
 * The result of loading and doing a small amount of verification on a field ...
 *
 * @author Amr Sabry
 * @version jdk-1.1 
 */

public class AbsynField {
    private int access_flags;
    private String name; 
    private Descriptor descriptor;
    private ConstantValueAttribute constantValue; // may be null

    public boolean isPublic () { return Modifier.isPublic(access_flags); }
    public boolean isPrivate () { return Modifier.isPrivate(access_flags); }
    public boolean isProtected () { return Modifier.isProtected(access_flags); }
    public boolean isStatic () { return Modifier.isStatic(access_flags); }
    public boolean isFinal () { return Modifier.isFinal(access_flags); }
    public boolean isVolatile () { return Modifier.isVolatile(access_flags); }
    public boolean isTransient () { return Modifier.isTransient(access_flags); }

    public String getName () { return name; }
    public Descriptor getType () { return descriptor; }
    public ConstantValueAttribute getConstantValue () { return constantValue; }

    AbsynField (ClassInputStream classFile, ConstantPool cp) throws ClassFileInputStreamE, ConstantPoolE {
        try {
            access_flags = classFile.readU2();

            int name_index = classFile.readU2();
            name = cp.getUtf8Entry(name_index).getString();

            int descriptor_index = classFile.readU2();
            descriptor = new Descriptor(cp.getUtf8Entry(descriptor_index).getString());

            int attributes_count = classFile.readU2();
            AbsynAttribute[] attributes = new AbsynAttribute[attributes_count];
            for (int i=0; i<attributes_count; i++) attributes[i] = AbsynAttribute.read(classFile, cp);
            for (int i=0; i<attributes.length; i++) {
                if (attributes[i].getName().equals("ConstantValue")) {
                    if (constantValue != null) throw new ConstantPoolE("More than one constant value attribute to field");
                    constantValue = (ConstantValueAttribute) attributes[i];
                }
            }
        }
        catch (BadDescriptorE e) { 
            throw new ConstantPoolE("Bad descriptor in constant pool " + e.getMessage()); 
        }
    }

    public String toString () {
        String res = "";
        res += isPublic() ? "public " : "";
        res += isPrivate() ? "private " : "";
        res += isProtected() ? "protected " : "";
        res += isStatic() ? "static " : "";
        res += isFinal() ? "final " : "";
        res += isVolatile() ? "volatile " : "";
        res += isTransient() ? "transient " : "";
        res += descriptor + " ";
        res += name + " ";
        res += "= " + constantValue; 
        return res;
    }
}
