package ojvm.loading.instructions;
                         
import ojvm.loading.ConstantPool;
import ojvm.loading.ConstantPoolE;
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.Descriptor;
import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an instanceof instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_instanceof extends Instruction {
  private Descriptor classDesc;

  public Ins_instanceof (InstructionInputStream classFile, ConstantPool cp) throws ClassFileInputStreamE, ConstantPoolE {
      super(RuntimeConstants.opc_instanceof);
      int index = classFile.readU2();
      classDesc = cp.getClassEntry(index).getDesc();
  }

  public Descriptor getClassDesc () { return classDesc; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_instanceof (this);
  }

  public String toString () {
    return opcodeName + " " + classDesc;
  }
}
