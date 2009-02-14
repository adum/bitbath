package ojvm.loading;
                         
import ojvm.util.Descriptor;

/**
 * The exceptions attribute of a method specifies which expections occur in its throws clause
 *
 * @author Amr Sabry
 * @version jdk-1.1 
 */

public class ExceptionsAttribute extends AbsynAttribute {
    private Descriptor[] exceptionDescs;

    ExceptionsAttribute (ClassInputStream classFile, ConstantPool cp, int length) 
        throws ClassFileInputStreamE, ConstantPoolE {

        int number_exceptions = classFile.readU2();
        exceptionDescs = new Descriptor[number_exceptions];
        for (int i=0; i<number_exceptions; i++) {
            int exception_index = classFile.readU2();
            exceptionDescs[i] = cp.getClassEntry(exception_index).getDesc();
        }
    }

    public String getName () { return "Exceptions"; }
    public Descriptor[] getExceptionTypes () { return exceptionDescs; }

    public String toString () { 
        String res = "";
        for (int i=0; i<exceptionDescs.length; i++) res += exceptionDescs[i] + " ";
        return res; 
    }
}
