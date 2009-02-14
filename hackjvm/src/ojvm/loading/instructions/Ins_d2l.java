package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a d2l instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_d2l extends Instruction {
  public Ins_d2l (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_d2l);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_d2l (this);
  }

  public String toString () {
    return opcodeName;
  }
}
