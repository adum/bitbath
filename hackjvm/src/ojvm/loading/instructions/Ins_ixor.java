package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a ixor instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_ixor extends Instruction {

  public Ins_ixor (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_ixor);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_ixor (this);
  }

  public String toString () {
    return opcodeName;
  }
}
