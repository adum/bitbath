package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an ret instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_ret extends Instruction {
  private int index; // index in local vars of current frame

  public Ins_ret (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_ret);
      this.index = classFile.readU1(); 
  }

  protected Ins_ret (int opcode, int index) {
    super(opcode);
    this.index = index;
  }

  public int getIndex () { return index; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_ret (this);
  }

  public String toString () {
    return opcodeName + " " + index;
  }
}
