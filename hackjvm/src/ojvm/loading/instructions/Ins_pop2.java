package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an pop2 instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_pop2 extends Instruction {

  public Ins_pop2 (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_pop2);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_pop2 (this);
  }

  public String toString () {
    return opcodeName;
  }
}
