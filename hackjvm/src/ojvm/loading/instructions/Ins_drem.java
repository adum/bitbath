package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a drem instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_drem extends Instruction {

  public Ins_drem (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_drem);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_drem (this);
  }

  public String toString () {
    return opcodeName;
  }
}
