package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an swap instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_swap extends Instruction {

  public Ins_swap (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_swap);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_swap (this);
  }

  public String toString () {
    return opcodeName;
  }
}
