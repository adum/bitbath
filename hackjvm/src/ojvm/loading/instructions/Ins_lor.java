package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a lor instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lor extends Instruction {

  public Ins_lor (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lor);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_lor (this);
  }

  public String toString () {
    return opcodeName;
  }
}
