package ojvm.loading;

/**
 * Constant pool entry for ints.
 * 
 * File created June 12, 2000
 * @author Amr Sabry
 **/

public class CPIntEntry extends CPEntry {
    private int value;

    public int getInt () { return value; }

    CPIntEntry (ClassInputStream classFile) throws ClassFileInputStreamE {
        value = classFile.readInt();
    }

    void resolve (ConstantPool cp) {
        return;
    }

    public String toString () { 
        return String.valueOf(value);
    }
}
