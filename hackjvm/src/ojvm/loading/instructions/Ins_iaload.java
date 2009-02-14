package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an iaload instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_iaload extends Instruction {

  public Ins_iaload (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_iaload);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_iaload (this);
  }

  public String toString () {
    return opcodeName;
  }
}
