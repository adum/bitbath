package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a fsub instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fsub extends Instruction {

  public Ins_fsub (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fsub);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_fsub (this);
  }

  public String toString () {
    return opcodeName;
  }
}
