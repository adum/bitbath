package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a lneg instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lneg extends Instruction {

  public Ins_lneg (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lneg);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_lneg (this);
  }

  public String toString () {
    return opcodeName;
  }
}
