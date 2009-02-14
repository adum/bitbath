package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a dup instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dup extends Instruction {

  public Ins_dup (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dup);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_dup (this);
  }

  public String toString () {
    return opcodeName;
  }
}
