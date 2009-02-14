package ojvm.loading;
                         
/**
 * The deprecated attribute (ignored)
 *
 * @author Amr Sabry
 * @version jdk-1.1 
 */

class DeprecatedAttribute extends AbsynAttribute {

    DeprecatedAttribute (ClassInputStream classFile, ConstantPool cp, int length) throws ConstantPoolE {
        if (length != 0) throw new ConstantPoolE("Bad length for \"Deprecated\" attribute");
    }

    public String getName () { return "Deprecated"; }

    public String toString () { 
        return getName();
    }
}
