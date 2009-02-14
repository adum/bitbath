package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a ishl instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_ishl extends Instruction {

  public Ins_ishl (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_ishl);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_ishl (this);
  }

  public String toString () {
    return opcodeName;
  }
}
