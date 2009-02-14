package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a lshr instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lshr extends Instruction {

  public Ins_lshr (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lshr);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_lshr (this);
  }

  public String toString () {
    return opcodeName;
  }
}
