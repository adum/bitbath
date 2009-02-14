package ojvm.loading;
                         
/**
 * The synthetic attribute (ignored)
 *
 * @author Amr Sabry
 * @version jdk-1.1 
 */

class SyntheticAttribute extends AbsynAttribute {

    SyntheticAttribute (ClassInputStream classFile, ConstantPool cp, int length) throws ConstantPoolE {
        if (length != 0) throw new ConstantPoolE("Bad length for \"Synthetic\" attribute");
    }

    public String getName () { return "Synthetic"; }

    public String toString () { 
        return getName(); 
    }
}
