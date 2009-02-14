package ojvm.loading;

import ojvm.util.Descriptor;
import ojvm.util.MethodDescriptor;
import ojvm.util.BadDescriptorE;

/**
 * Constant pool entry for methods. In a first pass read the
 * class_index and name_and_type_index in the constant pool being
 * constructed. In a second pass after the entire constant pool has
 * been constructed resolve the indices to actual names and
 * descriptors.
 * 
 * File created June 12, 2000
 * @author Amr Sabry
 **/

public class CPMethodEntry extends CPEntry {
    private int class_index;
    private int name_and_type_index;
    
    private boolean resolved;

    private Descriptor declaringClassDesc;
    private String methodName;
    private MethodDescriptor methodType;

    public Descriptor getDeclaringClassDesc () { return declaringClassDesc; }
    public String getMethodName () { return methodName; }
    public MethodDescriptor getMethodType () { return methodType; }

    CPMethodEntry (ClassInputStream classFile) throws ClassFileInputStreamE {
        class_index = classFile.readU2();
        name_and_type_index = classFile.readU2();
    }

    void resolve (ConstantPool cp) throws ConstantPoolE {
        try {
            if (resolved) return;
            resolved = true;

            CPClassEntry classEntry = cp.getClassEntry(class_index);
            classEntry.resolve(cp);
            declaringClassDesc = classEntry.getDesc();
        
            CPNameAndTypeEntry nameAndTypeEntry = cp.getNameAndTypeEntry(name_and_type_index);
            nameAndTypeEntry.resolve(cp);
            methodName = nameAndTypeEntry.getName();
            methodType = new MethodDescriptor(nameAndTypeEntry.getType());
        }
        catch (BadDescriptorE e) {
            throw new ConstantPoolE("Bad descriptor in constant pool: " + e.getMessage());
        }
    }

    public String toString () { 
        return "Method: " + declaringClassDesc + " :: " + methodName + " : " + methodType; 
    }
}

