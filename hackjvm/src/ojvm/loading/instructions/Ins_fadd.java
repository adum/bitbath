package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a fadd instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fadd extends Instruction {

  public Ins_fadd (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fadd);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_fadd (this);
  }

  public String toString () {
    return opcodeName;
  }
}
