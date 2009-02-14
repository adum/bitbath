package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an aastore instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_aastore extends Instruction {

  public Ins_aastore (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_aastore);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_aastore(this);
  }

  public String toString () {
    return opcodeName;
  }
}
