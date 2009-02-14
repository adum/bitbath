package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an monitorexit instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_monitorexit extends Instruction {

  public Ins_monitorexit (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_monitorexit);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_monitorexit (this);
  }

  public String toString () {
    return opcodeName;
  }
}
