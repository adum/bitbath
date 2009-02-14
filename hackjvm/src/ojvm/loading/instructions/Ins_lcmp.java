package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an lcmp instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lcmp extends Instruction {

  public Ins_lcmp (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lcmp);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_lcmp (this);
  }

  public String toString () {
    return opcodeName;
  }
}
