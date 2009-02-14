package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an faload instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_faload extends Instruction {

  public Ins_faload (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_faload);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_faload (this);
  }

  public String toString () {
    return opcodeName;
  }
}
