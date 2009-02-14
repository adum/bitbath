package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an sastore instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_sastore extends Instruction {

  public Ins_sastore (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_sastore);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_sastore (this);
  }

  public String toString () {
    return opcodeName;
  }
}
