package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a lshl instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lshl extends Instruction {

  public Ins_lshl (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lshl);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_lshl (this);
  }

  public String toString () {
    return opcodeName;
  }
}
