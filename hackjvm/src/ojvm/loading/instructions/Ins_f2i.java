package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a f2i instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_f2i extends Instruction {

  public Ins_f2i (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_f2i);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_f2i (this);
  }

  public String toString () {
    return opcodeName;
  }
}
