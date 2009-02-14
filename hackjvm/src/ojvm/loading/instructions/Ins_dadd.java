package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a dadd instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dadd extends Instruction {

  public Ins_dadd (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dadd);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_dadd (this);
  }

  public String toString () {
    return opcodeName;
  }
}
