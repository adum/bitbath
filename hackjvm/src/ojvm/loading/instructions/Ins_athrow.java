package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an athrow instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_athrow extends Instruction {

  public Ins_athrow (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_athrow);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_athrow (this);
  }

  public String toString () {
    return opcodeName;
  }
}
