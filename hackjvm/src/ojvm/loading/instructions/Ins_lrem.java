package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a lrem instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lrem extends Instruction {

  public Ins_lrem (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lrem);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_lrem (this);
  }

  public String toString () {
    return opcodeName;
  }
}
