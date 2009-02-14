package ojvm.loading;
                         
/**
 * Local variable table attribute (ignored)
 *
 * @author Amr Sabry
 * @version jdk-1.1 
 */

class LocalVariableTableAttribute extends AbsynAttribute {
    LocalVariableTableAttribute (ClassInputStream classFile, ConstantPool cp, int length) throws ClassFileInputStreamE {
        // We have no use for this attribute for now ... read and ignore 
        classFile.readAttribute(length); 
    }

    public String getName () { return "LocalVariableTable"; }

    public String toString () { 
        return getName();
    }
}
