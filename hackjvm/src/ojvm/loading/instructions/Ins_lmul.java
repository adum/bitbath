package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a lmul instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lmul extends Instruction {

  public Ins_lmul (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lmul);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_lmul (this);
  }

  public String toString () {
    return opcodeName;
  }
}
