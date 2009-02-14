package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor; 

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a dmul instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dmul extends Instruction {
  public Ins_dmul (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dmul);
  }

  public void accept (InstructionVisitor iv) throws JavaException {
    iv.visit_dmul (this);
  }

  public String toString () {
    return opcodeName;
  }
}
