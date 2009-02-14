package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a iushr instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_iushr extends Instruction {

  public Ins_iushr (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_iushr);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_iushr (this);
  }

  public String toString () {
    return opcodeName;
  }
}
