package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a idiv instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_idiv extends Instruction {

  public Ins_idiv (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_idiv);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_idiv (this);
  }

  public String toString () {
    return opcodeName;
  }
}
