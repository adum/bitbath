package ojvm.loading;

import ojvm.util.Descriptor;
import ojvm.util.BadDescriptorE;

/**
 * Constant pool entry for fields. In a first pass read the
 * class_index and name_and_type_index in the constant pool being
 * constructed. In a second pass after the entire constant pool has
 * been constructed resolve the indices to actual names and
 * descriptors.
 * 
 * File created June 12, 2000
 * @author Amr Sabry
 **/

public class CPFieldEntry extends CPEntry {
    private int class_index;
    private int name_and_type_index;

    private boolean resolved;

    private Descriptor declaringClassDesc;
    private String fieldName;
    private Descriptor fieldType;

    public Descriptor getDeclaringClassDesc () { return declaringClassDesc; }
    public String getFieldName () { return fieldName; }
    public Descriptor getFieldType () { return fieldType; }
    
    CPFieldEntry (ClassInputStream classFile) throws ClassFileInputStreamE {
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
            fieldName = nameAndTypeEntry.getName();
            fieldType = new Descriptor(nameAndTypeEntry.getType());
        }
        catch (BadDescriptorE e) {
            throw new ConstantPoolE("Bad descriptor in constant pool " + e.getMessage());
        }
    }

    public String toString () { 
        return "Field: " + declaringClassDesc + " :: " + fieldName + " : " + fieldType;
    }
}
