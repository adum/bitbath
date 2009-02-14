package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an daload instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_daload extends Instruction {

  public Ins_daload (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_daload);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_daload (this);
  }

  public String toString () {
    return opcodeName;
  }
}
