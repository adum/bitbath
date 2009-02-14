package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.machine.ThreadHaltE;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an areturn instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_areturn extends Instruction {

  public Ins_areturn (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_areturn);
  }

  public void accept (InstructionVisitor iv) throws JavaException, ThreadHaltE { 
    iv.visit_areturn (this);
  }

  public String toString () {
    return opcodeName;
  }
}
