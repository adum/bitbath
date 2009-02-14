package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a ineg instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_ineg extends Instruction {

  public Ins_ineg (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_ineg);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_ineg (this);
  }

  public String toString () {
    return opcodeName;
  }
}
