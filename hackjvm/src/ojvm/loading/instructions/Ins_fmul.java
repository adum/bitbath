package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a fmul instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fmul extends Instruction {

  public Ins_fmul (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fmul);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_fmul (this);
  }

  public String toString () {
    return opcodeName;
  }
}
