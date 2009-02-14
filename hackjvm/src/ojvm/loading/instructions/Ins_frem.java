package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a frem instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_frem extends Instruction {

  public Ins_frem (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_frem);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_frem (this);
  }

  public String toString () {
    return opcodeName;
  }
}
