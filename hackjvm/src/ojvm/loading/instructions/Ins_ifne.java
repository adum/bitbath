package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an ifne instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_ifne extends Instruction {
  private short offset;

  public Ins_ifne (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_ifne);
      this.offset = classFile.readShort();
  }

  public short getOffset () { return offset; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_ifne (this);
  }

  public String toString () {
    return opcodeName + " " + offset;
  }
}
