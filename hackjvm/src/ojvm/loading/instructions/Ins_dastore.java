package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an dastore instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dastore extends Instruction {

  public Ins_dastore (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dastore);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_dastore (this);
  }

  public String toString () {
    return opcodeName;
  }
}
