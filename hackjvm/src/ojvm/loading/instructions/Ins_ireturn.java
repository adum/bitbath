package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.machine.ThreadHaltE;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a ireturn instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_ireturn extends Instruction {

  public Ins_ireturn (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_ireturn);
  }

  public void accept (InstructionVisitor iv) throws JavaException, ThreadHaltE { 
    iv.visit_ireturn (this);
  }

  public String toString () {
    return opcodeName;
  }
}
