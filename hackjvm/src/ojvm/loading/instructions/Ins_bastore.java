package ojvm.loading.instructions;
                         
import ojvm.operations.InstructionVisitor;
import ojvm.data.JavaException;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an bastore instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_bastore extends Instruction {

  public Ins_bastore (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_bastore);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_bastore (this);
  }

  public String toString () {
    return opcodeName;
  }
}
