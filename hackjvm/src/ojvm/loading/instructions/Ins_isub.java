package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a isub instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_isub extends Instruction {

  public Ins_isub (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_isub);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_isub (this);
  }

  public String toString () {
    return opcodeName;
  }
}
