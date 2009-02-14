package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of a caload instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_caload extends Instruction {

  public Ins_caload (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_caload);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_caload (this);
  }

  public String toString () {
    return opcodeName;
  }
}
