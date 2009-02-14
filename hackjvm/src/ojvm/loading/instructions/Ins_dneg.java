package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a dneg instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dneg extends Instruction {

  public Ins_dneg (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dneg);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_dneg (this);
  }

  public String toString () {
    return opcodeName;
  }
}
