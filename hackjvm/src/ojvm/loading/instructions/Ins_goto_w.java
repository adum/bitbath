package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;


/**
 * The encapsulation of an goto_w instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_goto_w extends Instruction {
  private int offset;

  public Ins_goto_w (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_goto_w);
      this.offset = classFile.readInt();
  }

  public int getOffset () { return offset; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_goto_w (this);
  }

  public String toString () {
    return opcodeName + " " + offset;
  }
}
