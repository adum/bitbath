package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a irem instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_irem extends Instruction {

  public Ins_irem (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_irem);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_irem (this);
  }

  public String toString () {
    return opcodeName;
  }
}
