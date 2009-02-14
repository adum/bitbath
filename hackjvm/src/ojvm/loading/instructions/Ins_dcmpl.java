package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an dcmpl instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_dcmpl extends Instruction {
  public Ins_dcmpl (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_dcmpl);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_dcmpl (this);
  }

  public String toString () {
    return opcodeName;
  }
}
