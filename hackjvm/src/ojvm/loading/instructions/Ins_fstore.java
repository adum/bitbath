package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an fstore instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fstore extends Instruction {
  private int index;

  public Ins_fstore (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_fstore);
      this.index = classFile.readU1();
  }

  protected Ins_fstore (int opcode, int index) {
    super(opcode);
    this.index = index;
  }

  public int getIndex () { return index; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_fstore (this);
  }

  public String toString () {
    return opcodeName + " " + index;
  }
}
