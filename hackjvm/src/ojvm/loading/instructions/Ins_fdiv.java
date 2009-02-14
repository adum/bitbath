package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a fdiv instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fdiv extends Instruction {

  public Ins_fdiv (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fdiv);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_fdiv (this);
  }

  public String toString () {
    return opcodeName;
  }
}
