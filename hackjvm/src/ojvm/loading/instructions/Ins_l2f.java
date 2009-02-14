package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a l2f instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_l2f extends Instruction {

  public Ins_l2f (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_l2f);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_l2f (this);
  }

  public String toString () {
    return opcodeName;
  }
}
