package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an saload instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_saload extends Instruction {

  public Ins_saload (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_saload);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_saload (this);
  }

  public String toString () {
    return opcodeName;
  }
}
