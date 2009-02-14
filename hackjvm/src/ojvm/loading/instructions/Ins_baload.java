package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an baload instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_baload extends Instruction {

  public Ins_baload (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_baload);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_baload (this);
  }

  public String toString () {
    return opcodeName;
  }
}
