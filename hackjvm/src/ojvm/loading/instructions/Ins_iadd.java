package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a iadd instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_iadd extends Instruction {

  public Ins_iadd (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_iadd);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_iadd (this);
  }

  public String toString () {
    return opcodeName;
  }
}
