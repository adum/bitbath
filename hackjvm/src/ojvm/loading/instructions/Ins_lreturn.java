package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a lreturn instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lreturn extends Instruction {

  public Ins_lreturn (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lreturn);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_lreturn (this);
  }

  public String toString () {
    return opcodeName;
  }
}
