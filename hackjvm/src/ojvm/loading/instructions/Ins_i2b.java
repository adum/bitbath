package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a i2b instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_i2b extends Instruction {

  public Ins_i2b (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_i2b);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_i2b (this);
  }

  public String toString () {
    return opcodeName;
  }
}
