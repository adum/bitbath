package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an lload instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lload extends Instruction {
  protected int index;

  public Ins_lload (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_lload);
      this.index = classFile.readU1();
  }

  protected Ins_lload (int opcode, int index) {
    super(opcode);
    this.index = index;
  }

  public int getIndex () { return index; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_lload (this);
  }

  public String toString () {
    return opcodeName + " " + index;
  }
}
