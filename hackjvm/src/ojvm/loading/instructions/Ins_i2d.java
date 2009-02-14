package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a i2d instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_i2d extends Instruction {

  public Ins_i2d (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_i2d);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_i2d (this);
  }

  public String toString () {
    return opcodeName;
  }
}
