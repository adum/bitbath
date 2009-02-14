package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an laload instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_laload extends Instruction {

  public Ins_laload (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_laload);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_laload (this);
  }

  public String toString () {
    return opcodeName;
  }
}
