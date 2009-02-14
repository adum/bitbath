package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a fneg instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fneg extends Instruction {

  public Ins_fneg (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fneg);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_fneg (this);
  }

  public String toString () {
    return opcodeName;
  }
}
