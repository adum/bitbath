package ojvm.loading;

/**
 * Constant pool entry for longs
 * 
 * File created June 12, 2000
 * @author Amr Sabry
 **/

public class CPLongEntry extends CPEntry {
    private long value;

    public long getLong() { return value; }

    CPLongEntry (ClassInputStream classFile) throws ClassFileInputStreamE {
        value = classFile.readLong();
    }

    void resolve (ConstantPool cp) {
        return;
    }

    public String toString () { 
        return String.valueOf(value);
    }
}

