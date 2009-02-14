package ojvm.loading;

/**
 * Constant pool entry for "name and type". In a first pass read the
 * name_index and descriptor_index in the constant pool being
 * constructed. In a second pass after the entire constant pool has
 * been constructed resolve the indices to actual names and
 * descriptors. This entry is only referenced through other entries,
 * hence the class is not public.
 * 
 * File created June 12, 2000
 * @author Amr Sabry
 **/

class CPNameAndTypeEntry extends CPEntry {
    private int name_index; 
    private int descriptor_index;

    private boolean resolved;

    private String name;
    private String descriptor;

    String getName () { return name; }
    String getType () { return descriptor; }

    CPNameAndTypeEntry (ClassInputStream classFile) throws ClassFileInputStreamE {
        name_index = classFile.readU2();
        descriptor_index = classFile.readU2();
    }

    void resolve (ConstantPool cp) throws ConstantPoolE {
        if (resolved) return;
        resolved = true;

        name = cp.getUtf8Entry(name_index).getString();
        descriptor = cp.getUtf8Entry(descriptor_index).getString();
    }

    public String toString () {
        return "NameAndType: " + name + " : " + descriptor;
    }
}

