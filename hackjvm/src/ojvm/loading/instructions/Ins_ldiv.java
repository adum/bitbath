package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a ldiv instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_ldiv extends Instruction {

  public Ins_ldiv (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_ldiv);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_ldiv (this);
  }

  public String toString () {
    return opcodeName;
  }
}
