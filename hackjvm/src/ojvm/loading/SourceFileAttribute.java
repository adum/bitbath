package ojvm.loading;
                         
/**
 * Source file attribute (ignored)
 *
 * @author Amr Sabry
 * @version jdk-1.1 
 */

class SourceFileAttribute extends AbsynAttribute {
    SourceFileAttribute (ClassInputStream classFile, ConstantPool cp, int length) throws ClassFileInputStreamE {
        // We have no use for this attribute for now ... read and ignore 
        classFile.readAttribute(length); 
    }

    public String getName () { return "SourceFile"; }

    public String toString () { 
        return getName();
    }
}
