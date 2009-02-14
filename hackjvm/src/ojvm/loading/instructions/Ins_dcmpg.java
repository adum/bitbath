package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an dcmpg instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dcmpg extends Instruction {

  public Ins_dcmpg (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dcmpg);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_dcmpg (this);
  }

  public String toString () {
    return opcodeName;
  }
}
