package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a l2d instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_l2d extends Instruction {

  public Ins_l2d (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_l2d);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_l2d (this);
  }

  public String toString () {
    return opcodeName;
  }
}
