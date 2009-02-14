package ojvm.loading;

/**
 * Constant pool entry for strings. In a first pass read the
 * string_index in the constant pool being constructed. In a second
 * pass after the entire constant pool has been constructed resolve
 * the index to an actual string.
 *
 * File created June 12, 2000
 * @author Amr Sabry
 **/

public class CPStringEntry extends CPEntry {
    private int string_index;

    private boolean resolved;

    private String str; 

    public String getString () { return str; }

    CPStringEntry (ClassInputStream classFile) throws ClassFileInputStreamE {
        string_index = classFile.readU2();
    }

    void resolve (ConstantPool cp) throws ConstantPoolE {
        if (resolved) return;
        resolved = true;

        str = cp.getUtf8Entry(string_index).getString();
    }

    public String toString () { 
        return str;
    }
}
