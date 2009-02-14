package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a f2d instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_f2d extends Instruction {

  public Ins_f2d (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_f2d);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_f2d (this);
  }

  public String toString () {
    return opcodeName;
  }
}
