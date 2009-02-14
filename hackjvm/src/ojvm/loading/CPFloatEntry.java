package ojvm.loading;

/**
 * Constant pool entry for floats.
 *
 * File created June 12, 2000
 * @author Amr Sabry
 **/

public class CPFloatEntry extends CPEntry {
    private float value;

    public float getFloat () { return value; }

    CPFloatEntry (ClassInputStream classFile) throws ClassFileInputStreamE {
        value = classFile.readFloat();
    }

    void resolve (ConstantPool cp) {
        return;
    }

    public String toString () { 
        return String.valueOf(value);
    }
}
