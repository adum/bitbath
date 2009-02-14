package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a freturn instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_freturn extends Instruction {

  public Ins_freturn (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_freturn);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_freturn (this);
  }

  public String toString () {
    return opcodeName;
  }
}
