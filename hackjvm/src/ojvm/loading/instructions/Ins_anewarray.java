package ojvm.loading.instructions;
                         
import ojvm.loading.ConstantPool;
import ojvm.loading.ClassFileInputStreamE;
import ojvm.loading.ConstantPoolE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;
import ojvm.util.Descriptor;

/**
 * The encapsulation of an anewarray instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_anewarray extends Instruction {
    private Descriptor componentType;

    public Ins_anewarray (InstructionInputStream classFile, ConstantPool cp) throws ClassFileInputStreamE, ConstantPoolE {
        super(RuntimeConstants.opc_anewarray);
        int index = classFile.readU2();
        componentType = cp.getClassEntry(index).getDesc();
    }

    public Descriptor getComponentType () { return componentType; }

    public void accept (InstructionVisitor iv) throws JavaException {
        iv.visit_anewarray (this);
    }

    public String toString () {
        return opcodeName + " " + componentType;
    }
}
