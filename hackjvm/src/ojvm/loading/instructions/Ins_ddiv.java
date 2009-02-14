package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a ddiv instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_ddiv extends Instruction {

  public Ins_ddiv (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_ddiv);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_ddiv (this);
  }

  public String toString () {
    return opcodeName;
  }
}
