package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an fcmpg instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fcmpg extends Instruction {

  public Ins_fcmpg (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fcmpg);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_fcmpg (this);
  }

  public String toString () {
    return opcodeName;
  }
}
