package ojvm.loading;

/**
 * Constant pool entry for UTF8 constants. This entry is only
 * referenced through other entries, hence the class is not public.
 * 
 * File created June 12, 2000
 * @author Amr Sabry
 **/

class CPUtf8Entry extends CPEntry {
    private String str; 
    
    String getString () { return str; }

    CPUtf8Entry (ClassInputStream classFile) throws ClassFileInputStreamE {
        str = classFile.readUTF();
    }

    void resolve (ConstantPool cp) {
        return;
    }

    public String toString () {
        return str;
    }
}
