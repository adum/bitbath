package ojvm.loading;
                         
import ojvm.util.Descriptor;

/**
 * Needed to define one of the attributes of the code attribute
 *
 * @author Amr Sabry
 * @version jdk-1.1 
 **/

public class ExceptionHandler {
    private int start_pc, end_pc, handler_pc;
    private Descriptor catchType; // may be null which means catches all exceptions (needed for finally)

    ExceptionHandler (ClassInputStream classFile, ConstantPool cp) throws ClassFileInputStreamE, ConstantPoolE {
        start_pc = classFile.readU2();
        end_pc = classFile.readU2();
        handler_pc = classFile.readU2();
        int catch_index = classFile.readU2();
        if (catch_index != 0) catchType = cp.getClassEntry(catch_index).getDesc();
    }

    public int getStartPC () { return start_pc; }
    public int getEndPC () { return end_pc; }
    public int getHandlerPC () { return handler_pc; }
    public Descriptor getCatchType () { return catchType; }

    public String toString () { 
        String res = "Exception Handler\n";
        res += "start_pc = " + start_pc + "\n";
        res += "end_pc = " + end_pc + "\n";
        res += "handler_pc = " + handler_pc + "\n";
        res += "catchType = " + catchType + "\n";
        return res; 
    }
}
