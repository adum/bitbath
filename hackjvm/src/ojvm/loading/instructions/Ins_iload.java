package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an iload instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_iload extends Instruction {
  protected int index;

  public Ins_iload (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_iload);
      this.index = classFile.readU1();
  }

  protected Ins_iload (int opcode, int index) {
    super(opcode);
    this.index = index;
  }

  public int getIndex () { return index; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_iload (this);
  }

  public String toString () {
    return opcodeName + " " + index;
  }
}
