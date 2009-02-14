package ojvm.loading;

/**
 * A special tag to mark invalid entries in the constant pool (the
 * second word of a long/double).
 * 
 * File created June 12, 2000
 * @author Amr Sabry
 **/

class CPInvalidEntry extends CPEntry {
    CPInvalidEntry () {}

    void resolve (ConstantPool cp) {
        return;
    }

    public String toString () { 
        return "-";
    }
}
