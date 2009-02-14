package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a f2l instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_f2l extends Instruction {

  public Ins_f2l (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_f2l);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_f2l (this);
  }

  public String toString () {
    return opcodeName;
  }
}
