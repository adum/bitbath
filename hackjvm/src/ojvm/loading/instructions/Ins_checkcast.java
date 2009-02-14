package ojvm.loading.instructions;
                         
import ojvm.loading.ConstantPool;
import ojvm.loading.ConstantPoolE;
import ojvm.loading.ClassFileInputStreamE;

import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;
import ojvm.util.Descriptor;

/**
 * The encapsulation of a checkcast instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_checkcast extends Instruction {
  private Descriptor classDesc;

  public Ins_checkcast (InstructionInputStream classFile, ConstantPool cp) throws ClassFileInputStreamE, ConstantPoolE {
      super(RuntimeConstants.opc_checkcast);
      int index = classFile.readU2();
      classDesc = cp.getClassEntry(index).getDesc();
  }

  public Descriptor getClassDesc () { return classDesc; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_checkcast (this);
  }

  public String toString () {
    return opcodeName + " " + classDesc;
  }
}
