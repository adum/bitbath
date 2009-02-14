package ojvm.loading;
                         
/**
 * Line number table attribute (ignored)
 *
 * @author Amr Sabry
 * @version jdk-1.1 
 */

class LineNumberTableAttribute extends AbsynAttribute {
    LineNumberTableAttribute (ClassInputStream classFile, ConstantPool cp, int length) throws ClassFileInputStreamE {
        // We have no use for this attribute for now ... read and ignore 
        classFile.readAttribute(length); 
    }

    public String getName () { return "LineNumberTable"; }

    public String toString () { 
        return getName(); 
    }
}
