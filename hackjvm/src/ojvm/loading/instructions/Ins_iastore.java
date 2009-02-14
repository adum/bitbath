package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an iastore instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_iastore extends Instruction {

  public Ins_iastore (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_iastore);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_iastore (this);
  }

  public String toString () {
    return opcodeName;
  }
}
