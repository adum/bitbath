package ojvm.loading;

/**
 * Constant pool entry for doubles. 
 * 
 * File created June 12, 2000
 * @author Amr Sabry
 **/

public class CPDoubleEntry extends CPEntry {
    private double value;

    public double getDouble () { return value; }

    CPDoubleEntry (ClassInputStream classFile) throws ClassFileInputStreamE {
        value = classFile.readDouble();
    }

    void resolve (ConstantPool cp) {
        return;
    }

    public String toString () { 
        return String.valueOf(value);
    }
}

