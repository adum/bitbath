package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a dreturn instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dreturn extends Instruction {

  public Ins_dreturn (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dreturn);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_dreturn (this);
  }

  public String toString () {
    return opcodeName;
  }
}
