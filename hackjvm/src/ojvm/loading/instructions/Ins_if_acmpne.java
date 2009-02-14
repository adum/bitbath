package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;


/**
 * The encapsulation of an if_acmpne instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_if_acmpne extends Instruction {
  private short offset;

  public Ins_if_acmpne (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_if_acmpne);
      this.offset = classFile.readShort();
  }

  public short getOffset () { return offset; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_if_acmpne (this);
  }

  public String toString () {
    return opcodeName + " " + offset; 
  }
}
