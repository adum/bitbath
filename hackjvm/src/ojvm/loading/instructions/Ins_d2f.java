package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a d2f instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_d2f extends Instruction {

  public Ins_d2f (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_d2f);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_d2f (this);
  }

  public String toString () {
    return opcodeName;
  }
}
