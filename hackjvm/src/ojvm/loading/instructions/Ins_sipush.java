package ojvm.loading.instructions;
                         
import ojvm.loading.ClassFileInputStreamE;

import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a sipush instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_sipush extends Instruction {
  private short value; 

  public Ins_sipush (InstructionInputStream classFile) throws ClassFileInputStreamE {
      super(RuntimeConstants.opc_sipush);
      this.value = classFile.readShort();
  }

  public short getValue () { return value; }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_sipush (this);
  }

  public String toString () {
    return opcodeName + " " + value;
  }
}
