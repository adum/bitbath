package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a i2f instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_i2f extends Instruction {

  public Ins_i2f (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_i2f);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_i2f (this);
  }

  public String toString () {
    return opcodeName;
  }
}
