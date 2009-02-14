package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a land instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_land extends Instruction {

  public Ins_land (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_land);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_land (this);
  }

  public String toString () {
    return opcodeName;
  }
}
