package ojvm.loading;
                         
/**
 * Inner classes attributes (ignored)
 *
 * @author Amr Sabry
 * @version jdk-1.1 
 */

class InnerClassesAttribute extends AbsynAttribute {
    InnerClassesAttribute (ClassInputStream classFile, ConstantPool cp, int length) throws ClassFileInputStreamE {
        // We have no use for this attribute for now ... read and ignore 
        classFile.readAttribute(length); 
    }

    public String getName () { return "InnerClasses"; }

    public String toString () { 
        return getName();
    }
}
