package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;
import ojvm.machine.ThreadHaltE;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a return instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_return extends Instruction {

  public Ins_return (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_return);
  }

  public void accept (InstructionVisitor iv) throws JavaException, ThreadHaltE { 
    iv.visit_return (this);
  }

  public String toString () {
    return opcodeName;
  }
}
