package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a l2i instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_l2i extends Instruction {

  public Ins_l2i (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_l2i);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_l2i (this);
  }

  public String toString () {
    return opcodeName;
  }
}
