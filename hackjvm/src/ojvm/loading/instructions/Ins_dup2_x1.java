package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a dup2_x1 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dup2_x1 extends Instruction {

  public Ins_dup2_x1 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dup2_x1);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_dup2_x1 (this);
  }

  public String toString () {
    return opcodeName;
  }
}
