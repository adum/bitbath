package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an jsr_w instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_jsr_w extends Instruction {
  private int offset;

  public Ins_jsr_w (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_jsr_w);
      this.offset = classFile.readInt();
  }

  public int getOffset () { return offset; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_jsr_w (this);
  }

  public String toString () {
    return opcodeName + " " + offset;
  }
}
