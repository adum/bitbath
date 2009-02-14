package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an pop instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_pop extends Instruction {

  public Ins_pop (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_pop);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_pop (this);
  }

  public String toString () {
    return opcodeName;
  }
}
