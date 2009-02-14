package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a d2i instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_d2i extends Instruction {

  public Ins_d2i (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_d2i);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_d2i (this);
  }

  public String toString () {
    return opcodeName;
  }
}
