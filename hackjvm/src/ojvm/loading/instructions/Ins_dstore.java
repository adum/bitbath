package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an dstore instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dstore extends Instruction {
  private int index;

  public Ins_dstore (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_dstore);
      this.index = classFile.readU1();
  }

  protected Ins_dstore (int opcode, int index) {
    super(opcode);
    this.index = index;
  }

  public int getIndex () { return index; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_dstore (this);
  }

  public String toString () {
    return opcodeName + " " + index;
  }
}
