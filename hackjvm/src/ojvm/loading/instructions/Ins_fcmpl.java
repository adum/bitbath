package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an fcmpl instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fcmpl extends Instruction {

  public Ins_fcmpl (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fcmpl);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_fcmpl (this);
  }

  public String toString () {
    return opcodeName;
  }
}
