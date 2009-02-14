package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a i2s instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_i2s extends Instruction {

  public Ins_i2s (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_i2s);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_i2s (this);
  }

  public String toString () {
    return opcodeName;
  }
}
