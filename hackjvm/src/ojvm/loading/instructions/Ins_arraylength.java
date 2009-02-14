package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an arraylength instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_arraylength extends Instruction {

  public Ins_arraylength (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_arraylength);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_arraylength (this);
  }

  public String toString () {
    return opcodeName;
  }
}
