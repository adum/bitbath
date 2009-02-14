package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a ladd instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_ladd extends Instruction {

  public Ins_ladd (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_ladd);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_ladd (this);
  }

  public String toString () {
    return opcodeName;
  }
}
