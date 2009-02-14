package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;


/**
 * The encapsulation of an goto instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_goto extends Instruction {
  private short offset;

  public Ins_goto (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_goto);
      this.offset = classFile.readShort();
  }

  public short getOffset () { return offset; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_goto (this);
  }

  public String toString () {
    return opcodeName + " " + offset;
  }
}
