package ojvm.loading;
                         
/**
 * We must read and ignore unknown attributes
 *
 * @author Amr Sabry
 * @version jdk-1.1 
 */

class UnknownAttribute extends AbsynAttribute {
    private String name; 

    UnknownAttribute (String name, ClassInputStream classFile, int length) throws ClassFileInputStreamE {
        this.name = name;
        classFile.readAttribute(length); // read and silently ignore unknown attribute
    }

    public String getName () { return name; }

    public String toString () { 
        return getName(); 
    }
}
