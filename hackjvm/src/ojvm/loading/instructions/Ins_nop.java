package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an nop instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_nop extends Instruction {

  public Ins_nop (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_nop);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_nop (this);
  }

  public String toString () {
    return opcodeName;
  }
}
