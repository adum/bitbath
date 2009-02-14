package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a imul instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_imul extends Instruction {

  public Ins_imul (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_imul);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_imul (this);
  }

  public String toString () {
    return opcodeName;
  }
}
