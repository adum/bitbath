package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a i2l instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_i2l extends Instruction {

  public Ins_i2l (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_i2l);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_i2l (this);
  }

  public String toString () {
    return opcodeName;
  }
}
