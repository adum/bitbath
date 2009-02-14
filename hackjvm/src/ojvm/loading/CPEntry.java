package ojvm.loading;

/**
 * 
 * Root of constant pool entries ... The constructor for each constant
 * pool entry reads the indices it needs in the constant pool being
 * constructed. In a second pass after the entire constant pool has
 * been constructed resolve the indices to actual names and
 * descriptors.

 * 
 * File created June 12, 2000
 * @author Amr Sabry
 **/

public abstract class CPEntry {
    abstract void resolve (ConstantPool cp) throws ConstantPoolE;
}
