package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a ior instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_ior extends Instruction {

  public Ins_ior (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_ior);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_ior (this);
  }

  public String toString () {
    return opcodeName;
  }
}
