package ojvm.loading.instructions;
                         
import ojvm.data.JavaException;
import ojvm.operations.InstructionVisitor;

import ojvm.util.RuntimeConstants;

/**
 * The encapsulation of an fastore instruction. 
 * @author Amr Sabry
 * @version jdk-1.1
 */

public class Ins_fastore extends Instruction {

  public Ins_fastore (InstructionInputStream classFile) {
    super(RuntimeConstants.opc_fastore);
  }

  public void accept (InstructionVisitor iv) throws JavaException { 
    iv.visit_fastore (this);
  }

  public String toString () {
    return opcodeName;
  }
}
