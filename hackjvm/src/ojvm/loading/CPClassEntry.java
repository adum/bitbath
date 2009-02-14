package ojvm.loading;

import ojvm.util.Descriptor;
import ojvm.util.BadDescriptorE;

/**
 *
 * Constant pool entry for classes. In a first pass read the
 * name_index in the constant pool being constructed. In a second pass
 * after the entire constant pool has been constructed resolve the
 * index to an actual descriptor.
 * 
 * File created June 12, 2000
 * @author Amr Sabry
 **/

public class CPClassEntry extends CPEntry {
    private int name_index;

    private boolean resolved;

    private Descriptor desc;

    public Descriptor getDesc () { return desc; }

    CPClassEntry (ClassInputStream classFile) throws ClassFileInputStreamE {
        name_index = classFile.readU2();
    }

    void resolve (ConstantPool cp) throws ConstantPoolE {
        try {
            if (resolved) return; 
            resolved = true;

            String internalForm = cp.getUtf8Entry(name_index).getString();
            desc = new Descriptor(internalForm, Descriptor.INTERNAL_FORM);
        }
        catch (BadDescriptorE e) {
            throw new ConstantPoolE("Bad descriptor in constant pool " + e.getMessage());
        }
    }

    public String toString () { 
        return "Class: " + desc;
    }
}
