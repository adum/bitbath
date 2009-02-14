package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an lastore instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_lastore extends Instruction {

  public Ins_lastore (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_lastore);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_lastore (this);
  }

  public String toString () {
    return opcodeName;
  }
}
