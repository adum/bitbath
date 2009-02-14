package ojvm.loading;

import ojvm.util.Descriptor;

/**
 * 
 * Constant pool entry for interface methods. Essentially the same as
 * the one for methods.
 * 
 * File created June 12, 2000
 * @author Amr Sabry
 **/

public class CPInterfaceMethodEntry extends CPMethodEntry {

    CPInterfaceMethodEntry (ClassInputStream classFile) throws ClassFileInputStreamE {
        super(classFile);
    }

    public String toString () { 
        return "InterfaceMethod: " + super.toString(); 
    }
}

